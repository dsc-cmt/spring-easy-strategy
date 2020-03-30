package io.github.cmt.springstrategy;

/**
 * @param <R> 策略标识符 Key
 * @param <T> 策略接口 Value
 * @author shengchaojie
 * @date 2019-07-30
 * @see StrategyContainerFactoryBean
 * 类似Map,通过Key来获取Value
 **/

public interface StrategyContainer<R, T> {

    T getStrategy(R identify);

    void register(R identify, T strategy);

}
