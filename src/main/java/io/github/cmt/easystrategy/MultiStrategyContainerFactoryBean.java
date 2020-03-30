package io.github.cmt.easystrategy;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import lombok.Setter;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 支持identifycode对应多策略的场景
 * 支持继承org.springframework.core.Ordered接口 或 使用org.springframework.core.annotation.Order注解对策略进行排序
 * 排序优先级接口大于注解
 * @author shengchaojie
 * @date 2019-09-16
 **/
public class MultiStrategyContainerFactoryBean <R,T,V extends Annotation> implements FactoryBean<MultiStrategyContainer<R,T>>, ApplicationContextAware {

    @Setter
    private Class<T> strategyClass;

    @Setter
    private Class<V> strategyAnnotationClass;

    @Setter
    private Function<V,R> identifierGetter;

    private List<T> defaultStrategies = new ArrayList<>();

    private ListMultimap<R, T> strategyMultiMap = MultimapBuilder.ListMultimapBuilder.hashKeys().arrayListValues().build();

    public static <R,T,V extends Annotation> MultiStrategyContainerFactoryBean<R,T,V> build(Class<T> strategyClass, Class<V> strategyAnnotationClass , Function<V,R> identifierGetter) {
        MultiStrategyContainerFactoryBean<R,T,V> factoryBean = new MultiStrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(strategyClass);
        factoryBean.setStrategyAnnotationClass(strategyAnnotationClass);
        factoryBean.setIdentifierGetter(identifierGetter);
        return factoryBean;
    }

    @Override
    public MultiStrategyContainer<R,T> getObject() throws Exception {
        return new MultiStrategyContainer<R,T>() {
            @Override
            public List<T> getStrategies(R identifier) {
                List<T> strategies = strategyMultiMap.get(identifier);
                if(!CollectionUtils.isEmpty(strategies)){
                    return Collections.unmodifiableList(strategies);
                }
                return Collections.unmodifiableList(defaultStrategies);
            }
        };
    }

    @Override
    public Class<?> getObjectType() {
        return MultiStrategyContainer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] names = applicationContext.getBeanNamesForType(strategyClass);
        Arrays.stream(names).forEach(name->{
            T object = applicationContext.getBean(name,strategyClass);
            if(Objects.nonNull(AnnotationUtils.getAnnotation(AopUtils.getTargetClass(object),DefaultStrategy.class))){
                defaultStrategies.add(object);
            }
            AnnotationUtils.getRepeatableAnnotations(AopUtils.getTargetClass(object), strategyAnnotationClass)
                    .stream()
                    .map(identifierGetter)
                    .collect(Collectors.toSet())
                    .forEach(id->{
                        strategyMultiMap.put(id, object);
                    });
        });
        //sort
        strategyMultiMap.keys().forEach(key->{
            AnnotationAwareOrderComparator.sort(strategyMultiMap.get(key));
        });
    }
}
