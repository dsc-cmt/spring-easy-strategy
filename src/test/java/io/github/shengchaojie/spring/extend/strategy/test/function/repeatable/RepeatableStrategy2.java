package io.github.shengchaojie.spring.extend.strategy.test.function.repeatable;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
@One(test = "2")
public class RepeatableStrategy2 implements RepeatableStrategy {
    @Override
    public String test() {
        return "2";
    }
}
