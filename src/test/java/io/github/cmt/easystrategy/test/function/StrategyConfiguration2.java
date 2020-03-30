package io.github.cmt.easystrategy.test.function;

import com.google.common.base.Joiner;
import io.github.cmt.easystrategy.MultiStrategyContainerFactoryBean;
import io.github.cmt.easystrategy.StrategyContainerFactoryBean;
import io.github.cmt.easystrategy.StrategyIdentifier;
import io.github.cmt.easystrategy.test.function.common.*;
import io.github.cmt.easystrategy.test.function.multi.Validation;
import io.github.cmt.easystrategy.test.function.repeatable.One;
import io.github.cmt.easystrategy.test.function.repeatable.RepeatableStrategy;
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
     /*   StrategyContainerFactoryBean<PlatformStrategy, Platform, PlatformEnum> factoryBean = new StrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(PlatformStrategy.class);//策略接口
        factoryBean.setStrategyAnnotationClass(Platform.class);//策略实现类标注注解
        factoryBean.setIdentifierGetter(Platform::value);// 策略实现类标记注解到标识符的转换逻辑
        return factoryBean;*/
        return StrategyContainerFactoryBean.build(PlatformStrategy.class, Platform.class, Platform::value);
    }

    @Bean
    public StrategyContainerFactoryBean<RepeatableStrategy, One, String> repeatableStrategyManager() {
        return StrategyContainerFactoryBean.build(RepeatableStrategy.class, One.class, a -> a.test());
    }

    @Bean
    public MultiStrategyContainerFactoryBean<String,Validation, StrategyIdentifier> validation() {
        return MultiStrategyContainerFactoryBean.build(Validation.class, StrategyIdentifier.class, a -> a.identifyCode());
    }

}
