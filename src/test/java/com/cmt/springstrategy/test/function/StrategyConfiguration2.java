package com.cmt.springstrategy.test.function;

import com.cmt.springstrategy.test.function.common.*;
import com.google.common.base.Joiner;
import com.cmt.springstrategy.MultiStrategyContainerFactoryBean;
import com.cmt.springstrategy.StrategyContainerFactoryBean;
import com.cmt.springstrategy.StrategyIdentifier;
import com.cmt.springstrategy.test.function.multi.Validation;
import com.cmt.springstrategy.test.function.repeatable.One;
import com.cmt.springstrategy.test.function.repeatable.RepeatableStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@Configuration
public class StrategyConfiguration2 {

    @Bean
    public StrategyContainerFactoryBean<HelloStrategy, People, String> helloStrategyManager() {
        /*StrategyManagerFactoryBean<HelloStrategy, People> factoryBean = new StrategyManagerFactoryBean<>();
        factoryBean.setStrategyClass(HelloStrategy.class);
        factoryBean.setStrategyAnnotationClass(People.class);
        factoryBean.setIdentifyCodeGetter(a -> Joiner.on(",").join(a.district(),a.gender().name()));
        return factoryBean;*/
        return StrategyContainerFactoryBean.build(HelloStrategy.class, People.class, a -> Joiner.on(",").join(a.district(), a.gender().name()));
    }

    @Bean
    public StrategyContainerFactoryBean<PlatformStrategy, Platform, PlatformEnum> platformStrategyManager() {
        return StrategyContainerFactoryBean.build(PlatformStrategy.class, Platform.class, Platform::value);
    }

    @Bean
    public StrategyContainerFactoryBean<RepeatableStrategy, One, String> repeatableStrategyManager() {
        return StrategyContainerFactoryBean.build(RepeatableStrategy.class, One.class, a -> a.test());
    }

    @Bean
    public MultiStrategyContainerFactoryBean<Validation, StrategyIdentifier> validation() {
        return MultiStrategyContainerFactoryBean.build(Validation.class, StrategyIdentifier.class, a -> a.identifyCode());
    }

}
