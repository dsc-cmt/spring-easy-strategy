package com.scj.saber.strategy;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
public interface StrategyManager<T> {

    T getStrategy(String identifyCode);

}
