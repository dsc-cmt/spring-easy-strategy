package org.springframework.extend.stratrgy.test.demo.rewardpoints;

import org.springframework.extend.strategy.StrategyIdentifier;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@StrategyIdentifier(identifyCode = "LOW")
public class LowLevelPointsRewardStrategy implements PointsRewardStrategy{

    public Integer rewardPoints(Integer price) {
        System.out.println("返百分之5积分");
        return price /10000 * 5;
    }
}
