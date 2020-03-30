package io.github.cmt.easystrategy.test.function.common;

import io.github.cmt.easystrategy.DefaultStrategy;

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
