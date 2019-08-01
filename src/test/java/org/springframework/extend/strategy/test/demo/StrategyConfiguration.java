package org.springframework.extend.strategy.test.demo;

import org.springframework.extend.strategy.StrategyIdentifier;
import org.springframework.extend.strategy.StrategyManagerFactoryBean;
import org.springframework.extend.strategy.test.demo.calculateprice.CalculatePriceStrategy;
import org.springframework.extend.strategy.test.demo.rewardpoints.PointsRewardStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@Configuration
public class StrategyConfiguration {

    @Bean
    public StrategyManagerFactoryBean calculatePriceStrategyManager2(){
        return StrategyManagerFactoryBean.build(CalculatePriceStrategy.class,StrategyIdentifier.class,(a)->a.identifyCode());
    }

    @Bean
    public StrategyManagerFactoryBean<PointsRewardStrategy, StrategyIdentifier> pointsRewardStrategyManager3(){
        StrategyManagerFactoryBean<PointsRewardStrategy, StrategyIdentifier> factoryBean = new StrategyManagerFactoryBean<>();
        factoryBean.setStrategyClass(PointsRewardStrategy.class);
        factoryBean.setStrategyAnnotationClass(StrategyIdentifier.class);
        factoryBean.setIdentifyCodeGetter((a)->a.identifyCode());
        return factoryBean;
    }

}
