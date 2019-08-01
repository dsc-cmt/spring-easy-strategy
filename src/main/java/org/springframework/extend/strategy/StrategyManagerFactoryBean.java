package org.springframework.extend.strategy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
public class StrategyManagerFactoryBean<T,V extends Annotation> implements FactoryBean<StrategyManager>, ApplicationContextAware {

    private Class<T> strategyClass;

    private Class<V> strategyAnnotationClass;

    private Function<V,String> identifyCodeGetter;

    private Map<String,T> strategyTable = new HashMap<>();

    @Override
    public StrategyManager<T> getObject() throws Exception {
        return new StrategyManager<T>() {
            @Override
            public T getStrategy(String identifyCode) {
                return strategyTable.get(identifyCode);
            }

            @Override
            public void register(String identifyCode, T strategy) {
                strategyTable.put(identifyCode,strategy);
            }
        };
    }

    public void setStrategyClass(Class<T> strategyClass) {
        this.strategyClass = strategyClass;
    }

    public void setStrategyAnnotationClass(Class<V> strategyAnnotationClass) {
        this.strategyAnnotationClass = strategyAnnotationClass;
    }

    public void setIdentifyCodeGetter(Function<V, String> identifyCodeGetter) {
        this.identifyCodeGetter = identifyCodeGetter;
    }

    @Override
    public Class<?> getObjectType() {
        return StrategyManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String names[] = applicationContext.getBeanNamesForType(strategyClass);
        Arrays.stream(names).forEach(name->{
            T object = applicationContext.getBean(name,strategyClass);
            V identifier = AnnotationUtils.getAnnotation(object.getClass(),strategyAnnotationClass);
            if(Objects.nonNull(identifier)){
                strategyTable.put(identifyCodeGetter.apply(identifier),object);
            }
        });
    }
}
