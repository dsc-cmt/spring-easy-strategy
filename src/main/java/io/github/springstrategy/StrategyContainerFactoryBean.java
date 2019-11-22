package io.github.springstrategy;

import com.google.common.collect.Lists;
import io.github.springstrategy.exceptions.StrategyException;
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
 * @author shengchaojie
 * @date 2019-07-30
 **/
public class StrategyContainerFactoryBean<T,V extends Annotation> implements FactoryBean<StrategyContainer<T>>, ApplicationContextAware {

    @Setter
    private Class<T> strategyClass;

    @Setter
    private Class<V> strategyAnnotationClass;

    @Setter
    private Function<V,String> identifyCodeGetter;

    private Map<String,T> strategyTable = new HashMap<>();

    private T defaultStrategy;

    public static <T,V extends Annotation> StrategyContainerFactoryBean<T,V> build(Class<T> strategyClass, Class<V> strategyAnnotationClass , Function<V,String> identifyCodeGetter) {
        StrategyContainerFactoryBean<T,V> factoryBean = new StrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(strategyClass);
        factoryBean.setStrategyAnnotationClass(strategyAnnotationClass);
        factoryBean.setIdentifyCodeGetter(identifyCodeGetter);
        return factoryBean;
    }

    @Override
    public StrategyContainer<T> getObject() throws Exception {
        Assert.notNull(strategyClass,"strategyClass must not be null");
        Assert.notNull(strategyAnnotationClass,"strategyAnnotationClass must not be null");
        Assert.notNull(identifyCodeGetter,"identifyCodeGetter must not be null");

        return new StrategyContainer<T>() {
            @Override
            public T getStrategy(String identifyCode) {
                return Optional.ofNullable(strategyTable.get(identifyCode)).orElse(defaultStrategy);
            }

            @Override
            public void register(String identifyCode, T strategy) {
                strategyTable.put(identifyCode,strategy);
            }
        };
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
        Arrays.stream(names).forEach(name->{
            T object = applicationContext.getBean(name,strategyClass);
            if(Objects.nonNull(AnnotationUtils.getAnnotation(AopUtils.getTargetClass(object),DefaultStrategy.class))){
                if(Objects.nonNull(defaultStrategy)){
                    throw new StrategyException("StrategyClass="+strategyClass.getName()+"can only have one default strategy");
                }else{
                    defaultStrategy = object;
                }
            }
            List<V> identifiers = Lists.newArrayList();
            identifiers.addAll(AnnotationUtils.getRepeatableAnnotations(AopUtils.getTargetClass(object), strategyAnnotationClass));
            if(!CollectionUtils.isEmpty(identifiers)){
                identifiers.forEach(i->{
                    String identifyCode = identifyCodeGetter.apply(i);
                    if(Objects.nonNull(strategyTable.putIfAbsent(identifyCode,object))){
                        throw new StrategyException("StrategyClass="+strategyClass.getName()+",identifyCode="+identifyCode+"exist multi config");
                    }
                });
            }
        });
    }

}
