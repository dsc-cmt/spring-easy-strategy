package io.github.springstrategy;

import java.util.List;

/**
 * @author shengchaojie
 * @date 2019-09-16
 **/
public interface MultiStrategyContainer<T> {

    List<T> getStrategies(String identifyCode);

}
