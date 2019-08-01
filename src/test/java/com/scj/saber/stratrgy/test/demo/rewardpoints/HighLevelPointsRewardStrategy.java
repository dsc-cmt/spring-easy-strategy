package com.scj.saber.stratrgy.test.demo.rewardpoints;

import com.scj.saber.strategy.StrategyIdentifier;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@StrategyIdentifier(identifyCode = "HIGH")
public class HighLevelPointsRewardStrategy implements PointsRewardStrategy{
    public Integer rewardPoints(Integer price) {
        System.out.println("返百分之15积分");
        return price /10000 * 15;
    }
}
