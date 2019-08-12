package org.springframework.extend.strategy.test.demo;

import org.springframework.extend.strategy.StrategyIdentifier;
import org.springframework.extend.strategy.StrategyContainerFactoryBean;
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
    public StrategyContainerFactoryBean calculatePriceStrategyManager2(){
        return StrategyContainerFactoryBean.build(CalculatePriceStrategy.class,StrategyIdentifier.class,(a)->a.identifyCode());
    }

    @Bean
    public StrategyContainerFactoryBean<PointsRewardStrategy, StrategyIdentifier> pointsRewardStrategyManager3(){
        StrategyContainerFactoryBean<PointsRewardStrategy, StrategyIdentifier> factoryBean = new StrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(PointsRewardStrategy.class);
        factoryBean.setStrategyAnnotationClass(StrategyIdentifier.class);
        factoryBean.setIdentifyCodeGetter((a)->a.identifyCode());
        return factoryBean;
    }

}
