package io.github.shengchaojie.springstrategy.test.function.common;

import io.github.shengchaojie.springstrategy.DefaultStrategy;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
@DefaultStrategy
public class DefaultHelloStrategy implements HelloStrategy{
    @Override
    public String hello() {
        return "default";
    }
}
