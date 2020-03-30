package com.cmt.springstrategy;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
public interface StrategyContainer<R, T> {

    T getStrategy(R identify);

    void register(R identify, T strategy);

}
