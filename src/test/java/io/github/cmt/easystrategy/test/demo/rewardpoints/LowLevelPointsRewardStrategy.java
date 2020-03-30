package io.github.cmt.easystrategy.test.demo.rewardpoints;

import io.github.cmt.easystrategy.StrategyIdentifier;

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
