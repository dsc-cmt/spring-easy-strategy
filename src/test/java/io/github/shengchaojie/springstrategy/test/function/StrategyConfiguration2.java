package io.github.shengchaojie.springstrategy.test.function;

import com.google.common.base.Joiner;
import io.github.shengchaojie.springstrategy.MultiStrategyContainerFactoryBean;
import io.github.shengchaojie.springstrategy.StrategyContainerFactoryBean;
import io.github.shengchaojie.springstrategy.StrategyIdentifier;
import io.github.shengchaojie.springstrategy.test.function.common.HelloStrategy;
import io.github.shengchaojie.springstrategy.test.function.common.People;
import io.github.shengchaojie.springstrategy.test.function.multi.Validation;
import io.github.shengchaojie.springstrategy.test.function.repeatable.One;
import io.github.shengchaojie.springstrategy.test.function.repeatable.RepeatableStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@Configuration
public class StrategyConfiguration2 {

    @Bean
    public StrategyContainerFactoryBean<HelloStrategy, People> helloStrategyManager(){
        /*StrategyManagerFactoryBean<HelloStrategy, People> factoryBean = new StrategyManagerFactoryBean<>();
        factoryBean.setStrategyClass(HelloStrategy.class);
        factoryBean.setStrategyAnnotationClass(People.class);
        factoryBean.setIdentifyCodeGetter(a -> Joiner.on(",").join(a.district(),a.gender().name()));
        return factoryBean;*/
        return StrategyContainerFactoryBean.build(HelloStrategy.class,People.class, a -> Joiner.on(",").join(a.district(),a.gender().name()));
    }

    @Bean
    public StrategyContainerFactoryBean<RepeatableStrategy, One> repeatableStrategyManager(){
        return StrategyContainerFactoryBean.build(RepeatableStrategy.class,One.class, a -> a.test());
    }

    @Bean
    public MultiStrategyContainerFactoryBean<Validation, StrategyIdentifier> validation(){
        return MultiStrategyContainerFactoryBean.build(Validation.class,StrategyIdentifier.class,a -> a.identifyCode());
    }

}
