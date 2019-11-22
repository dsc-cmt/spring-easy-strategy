package io.github.springstrategy.test.demo;

import io.github.springstrategy.StrategyContainerFactoryBean;
import io.github.springstrategy.test.demo.calculateprice.CalculatePriceStrategy;
import io.github.springstrategy.test.demo.rewardpoints.PointsRewardStrategy;
import io.github.springstrategy.StrategyIdentifier;
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
