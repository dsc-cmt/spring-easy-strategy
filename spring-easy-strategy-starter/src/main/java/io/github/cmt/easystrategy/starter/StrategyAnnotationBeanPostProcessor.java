package io.github.cmt.easystrategy.starter;

import io.github.cmt.easystrategy.exceptions.StrategyException;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;

/**
 * @author shengchaojie
 * @date 2020/11/20
 **/
public class StrategyAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
        implements MergedBeanDefinitionPostProcessor, PriorityOrdered, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final Map<StrategyCacheKey, Object> CACHE = new HashMap<>();

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {

        InjectionMetadata metadata = findStrategyMetadata(bean.getClass());
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of @EasyStrategy dependencies failed", ex);
        }
        return pvs;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    private InjectionMetadata findStrategyMetadata(final Class<?> beanClass) {
        List<InjectionMetadata.InjectedElement> elements = new ArrayList<>();

        ReflectionUtils.doWithFields(beanClass, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

                EasyStrategy reference = getAnnotation(field, EasyStrategy.class);

                if (reference != null) {

                    if (Modifier.isStatic(field.getModifiers())) {
                        return;
                    }

                    elements.add(new StrategyFieldElement(field, reference, applicationContext));
                }

            }
        });

        return new InjectionMetadata(beanClass, elements);
    }

    private static class StrategyFieldElement extends InjectionMetadata.InjectedElement {

        private final Field field;

        private final EasyStrategy easyStrategy;

        private final ApplicationContext applicationContext;

        protected StrategyFieldElement(Field member, EasyStrategy easyStrategy, ApplicationContext applicationContext) {
            super(member, null);
            this.field = member;
            this.easyStrategy = easyStrategy;
            this.applicationContext = applicationContext;
        }

        @Override
        protected void inject(Object target, String requestingBeanName, PropertyValues pvs) throws Throwable {
            ResolvableType resolvableType = ResolvableType.forField(field);
            Class<?> strategyClass = null;
            if (ResolvableType.forClass(StrategyContainer.class).isAssignableFrom(resolvableType)) {
                strategyClass = resolvableType.getGeneric(0).getRawClass();
            } else if (ResolvableType.forClass(io.github.cmt.easystrategy.StrategyContainer.class).isAssignableFrom(resolvableType)) {
                if (!resolvableType.getGeneric(0).isAssignableFrom(String.class)) {
                    throw new StrategyException("first generic type must be String!");
                }
                strategyClass = resolvableType.getGeneric(1).getRawClass();
            }
            ReflectionUtils.makeAccessible(field);
            StrategyCacheKey strategyCacheKey = new StrategyCacheKey(resolvableType.getRawClass(), strategyClass);
            Object container = CACHE.get(strategyCacheKey);
            if (Objects.isNull(container)) {
                NormalStrategyContainerFactoryBean normalStrategyContainerFactoryBean = new NormalStrategyContainerFactoryBean(strategyClass);
                normalStrategyContainerFactoryBean.setApplicationContext(applicationContext);
                container = normalStrategyContainerFactoryBean.getObject();
                CACHE.put(strategyCacheKey, container);
            }

            field.set(target, container);
        }
    }

    private static class StrategyCacheKey {

        private Class<?> containerClass;

        private Class<?> strategyClass;

        public StrategyCacheKey(Class<?> containerClass, Class<?> strategyClass) {
            this.containerClass = containerClass;
            this.strategyClass = strategyClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StrategyCacheKey that = (StrategyCacheKey) o;
            return Objects.equals(containerClass, that.containerClass) && Objects.equals(strategyClass, that.strategyClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(containerClass, strategyClass);
        }
    }

}
