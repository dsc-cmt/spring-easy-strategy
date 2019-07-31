package com.scj.saber.stratrgy.test.config;

import com.scj.saber.strategy.StrategyManagerFactoryBean;
import com.scj.saber.stratrgy.test.calculateprice.CalculatePriceStrategy;
import com.scj.saber.stratrgy.test.rewardpoints.PointsRewardStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@Configuration
public class StrategyConfiguration {

    @Bean
    public StrategyManagerFactoryBean<CalculatePriceStrategy> calculatePriceStrategyManager(){
        StrategyManagerFactoryBean<CalculatePriceStrategy> factoryBean = new StrategyManagerFactoryBean<CalculatePriceStrategy>();
        factoryBean.setStrategyClass(CalculatePriceStrategy.class);
        return factoryBean;
    }

    @Bean
    public StrategyManagerFactoryBean<PointsRewardStrategy> pointsRewardStrategyManager(){
        StrategyManagerFactoryBean<PointsRewardStrategy> factoryBean = new StrategyManagerFactoryBean<PointsRewardStrategy>();
        factoryBean.setStrategyClass(PointsRewardStrategy.class);
        return factoryBean;
    }

}
