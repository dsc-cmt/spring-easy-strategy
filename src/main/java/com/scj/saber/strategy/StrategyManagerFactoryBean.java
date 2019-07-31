package com.scj.saber.strategy;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
public class StrategyManagerFactoryBean<T> implements FactoryBean<StrategyManager>, ApplicationContextAware {

    private Class<T> strategyClass;

    private Table<Class<T>,String,T> strategyTable = HashBasedTable.create();

    @Override
    public StrategyManager<T> getObject() throws Exception {
        return new StrategyManager<T>() {
            @Override
            public T getStrategy(String identifyCode) {
                T obj = strategyTable.get(strategyClass,identifyCode);
                Assert.notNull(obj,"查找"+ strategyClass.getName() +"策略"+ identifyCode +"失败");
                return obj;
            }
        };
    }

    public void setStrategyClass(Class<T> strategyClass) {
        this.strategyClass = strategyClass;
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
        String[] names = applicationContext.getBeanNamesForType(strategyClass);
        Arrays.stream(names).forEach(name->{
            T object = applicationContext.getBean(name,strategyClass);
            StrategyIdentifier identifier = AnnotationUtils.getAnnotation(object.getClass(),StrategyIdentifier.class);
            if(Objects.nonNull(identifier)){
                strategyTable.put(strategyClass,identifier.identifyCode(),object);
            }
        });
    }
}
