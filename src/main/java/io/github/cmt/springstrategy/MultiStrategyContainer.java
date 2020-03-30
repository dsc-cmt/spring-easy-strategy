package io.github.cmt.springstrategy;

import java.util.List;

/**
 * R 唯一标识
 * T 策略接口
 * @author shengchaojie
 * @date 2019-09-16
 **/
public interface MultiStrategyContainer<R,T> {

    /**
     * 通过identify获取对应多个策略实现
     * @param identify 标识符
     * @return 策略实现List
     */
    List<T> getStrategies(R identify);

}
