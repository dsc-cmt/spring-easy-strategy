package com.cmt.springstrategy.test.demo;

import com.cmt.springstrategy.StrategyContainerFactoryBean;
import com.cmt.springstrategy.test.demo.calculateprice.CalculatePriceStrategy;
import com.cmt.springstrategy.test.demo.rewardpoints.PointsRewardStrategy;
import com.cmt.springstrategy.StrategyIdentifier;
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
    public StrategyContainerFactoryBean<PointsRewardStrategy, StrategyIdentifier,String> pointsRewardStrategyManager3(){
        StrategyContainerFactoryBean<PointsRewardStrategy, StrategyIdentifier,String> factoryBean = new StrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(PointsRewardStrategy.class);
        factoryBean.setStrategyAnnotationClass(StrategyIdentifier.class);
        factoryBean.setIdentifyCodeGetter((a)->a.identifyCode());
        return factoryBean;
    }

}
