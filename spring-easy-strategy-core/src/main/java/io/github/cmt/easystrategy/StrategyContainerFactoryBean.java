package io.github.cmt.easystrategy;

import io.github.cmt.easystrategy.exceptions.StrategyException;
import com.google.common.collect.Lists;
import lombok.Setter;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;

/**
 * @param <T> 策略接口
 * @param <V> 策略实现类的标注注解
 * @param <R> 策略标识符,可以为数组，枚举
 * @author shengchaojie
 * @date 2019-07-30
 **/
public class StrategyContainerFactoryBean<T, V extends Annotation, R> implements FactoryBean<StrategyContainer<R, T>>, ApplicationContextAware {

    @Setter
    protected Class<T> strategyClass;

    @Setter
    protected Class<V> strategyAnnotationClass;

    @Setter
    protected Function<V, R> identifierGetter;

    protected Map<R, T> strategyTable = new HashMap<>();

    private T defaultStrategy;

    public StrategyContainerFactoryBean() {
    }

    public StrategyContainerFactoryBean(Class<T> strategyClass, Class<V> strategyAnnotationClass, Function<V, R> identifierGetter) {
        this.strategyClass = strategyClass;
        this.strategyAnnotationClass = strategyAnnotationClass;
        this.identifierGetter = identifierGetter;
    }

    public static <T, V extends Annotation, R> StrategyContainerFactoryBean<T, V, R> build(Class<T> strategyClass, Class<V> strategyAnnotationClass, Function<V, R> identifyGetter) {
        StrategyContainerFactoryBean<T, V, R> factoryBean = new StrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(strategyClass);
        factoryBean.setStrategyAnnotationClass(strategyAnnotationClass);
        factoryBean.setIdentifierGetter(identifyGetter);
        return factoryBean;
    }

    @Override
    public StrategyContainer<R, T> getObject() throws Exception {
        Assert.notNull(strategyClass, "strategyClass must not be null");
        Assert.notNull(strategyAnnotationClass, "strategyAnnotationClass must not be null");
        Assert.notNull(identifierGetter, "identifierGetter must not be null");

        return new StrategyContainer<R, T>() {
            @Override
            public T getStrategy(R identify) {
                return getStrategyFromContainer(identify);
            }

            @Override
            public void register(R identify, T strategy) {
                strategyTable.put(identify, strategy);
            }
        };
    }

    /**
     * 抽象通过identify获取策略逻辑
     *
     * 如果有自定义获取逻辑
     * 可以进行重载
     * 比如类似cola extension的逻辑
     * @param identify
     * @return
     */
    protected T getStrategyFromContainer(R identify){
        return Optional.ofNullable(strategyTable.get(identify)).orElse(defaultStrategy);
    }

    @Override
    public Class<?> getObjectType() {
        return StrategyContainer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] names = applicationContext.getBeanNamesForType(strategyClass);
        Arrays.stream(names).forEach(name -> {
            T object = applicationContext.getBean(name, strategyClass);
            if (Objects.nonNull(AnnotationUtils.getAnnotation(AopUtils.getTargetClass(object), DefaultStrategy.class))) {
                if (Objects.nonNull(defaultStrategy)) {
                    throw new StrategyException("StrategyClass=" + strategyClass.getName() + "can only have one default strategy");
                } else {
                    defaultStrategy = object;
                }
            }
            List<V> identifiers = Lists.newArrayList();
            identifiers.addAll(AnnotationUtils.getRepeatableAnnotations(AopUtils.getTargetClass(object), strategyAnnotationClass));
            if (!CollectionUtils.isEmpty(identifiers)) {
                identifiers.forEach(i -> {
                    R identify = identifierGetter.apply(i);
                    if (Objects.nonNull(strategyTable.putIfAbsent(identify, object))) {
                        throw new StrategyException("StrategyClass=" + strategyClass.getName() + ",identify=" + identify + "exist multi config");
                    }
                });
            }
        });
    }

}
