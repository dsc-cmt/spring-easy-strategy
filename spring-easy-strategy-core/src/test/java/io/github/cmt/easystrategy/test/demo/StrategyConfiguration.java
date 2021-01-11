package io.github.cmt.easystrategy.test.demo;

import io.github.cmt.easystrategy.StrategyContainerFactoryBean;
import io.github.cmt.easystrategy.test.demo.calculateprice.CalculatePriceStrategy;
import io.github.cmt.easystrategy.test.demo.rewardpoints.PointsRewardStrategy;
import io.github.cmt.easystrategy.StrategyIdentifier;
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
        return StrategyContainerFactoryBean.build(CalculatePriceStrategy.class, StrategyIdentifier.class,(a)->a.identifyCode());
    }

    @Bean
    public StrategyContainerFactoryBean<PointsRewardStrategy, StrategyIdentifier,String> pointsRewardStrategyManager3(){
        StrategyContainerFactoryBean<PointsRewardStrategy, StrategyIdentifier,String> factoryBean = new StrategyContainerFactoryBean<>();
        factoryBean.setStrategyClass(PointsRewardStrategy.class);
        factoryBean.setStrategyAnnotationClass(StrategyIdentifier.class);
        factoryBean.setIdentifierGetter((a)->a.identifyCode());
        return factoryBean;
    }

}
