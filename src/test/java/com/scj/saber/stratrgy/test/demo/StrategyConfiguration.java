package com.scj.saber.stratrgy.test.demo;

import com.scj.saber.strategy.StrategyIdentifier;
import com.scj.saber.strategy.StrategyManagerFactoryBean;
import com.scj.saber.stratrgy.test.demo.calculateprice.CalculatePriceStrategy;
import com.scj.saber.stratrgy.test.demo.rewardpoints.PointsRewardStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@Configuration
public class StrategyConfiguration {

    @Bean
    public StrategyManagerFactoryBean<CalculatePriceStrategy, StrategyIdentifier> calculatePriceStrategyManager(){
        StrategyManagerFactoryBean<CalculatePriceStrategy, StrategyIdentifier> factoryBean = new StrategyManagerFactoryBean<>();
        factoryBean.setStrategyClass(CalculatePriceStrategy.class);
        factoryBean.setStrategyAnnotationClass(StrategyIdentifier.class);
        factoryBean.setIdentifyCodeGetter((a)->a.identifyCode());
        return factoryBean;
    }

    @Bean
    public StrategyManagerFactoryBean<PointsRewardStrategy, StrategyIdentifier> pointsRewardStrategyManager(){
        StrategyManagerFactoryBean<PointsRewardStrategy, StrategyIdentifier> factoryBean = new StrategyManagerFactoryBean<>();
        factoryBean.setStrategyClass(PointsRewardStrategy.class);
        factoryBean.setStrategyAnnotationClass(StrategyIdentifier.class);
        factoryBean.setIdentifyCodeGetter((a)->a.identifyCode());
        return factoryBean;
    }

}
