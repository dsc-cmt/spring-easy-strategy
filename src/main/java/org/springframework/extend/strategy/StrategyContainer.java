package org.springframework.extend.strategy;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
public interface StrategyContainer<T> {

    T getStrategy(String identifyCode);

    void register(String identifyCode,T strategy);

}
