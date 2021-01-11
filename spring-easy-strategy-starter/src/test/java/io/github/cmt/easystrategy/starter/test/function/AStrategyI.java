package io.github.cmt.easystrategy.starter.test.function;

import io.github.cmt.easystrategy.StrategyIdentifier;

/**
 * @author shengchaojie
 * @date 2020/12/6
 **/
@StrategyIdentifier(identifyCode = "A")
public class AStrategyI implements StrategyI{
    @Override
    public String test() {
        System.out.println("A");
        return "A";
    }
}
