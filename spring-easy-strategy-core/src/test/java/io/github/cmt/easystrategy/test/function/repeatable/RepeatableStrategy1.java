package io.github.cmt.easystrategy.test.function.repeatable;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
@One(test = "1")
@One(test = "3")
public class RepeatableStrategy1 implements RepeatableStrategy {
    @Override
    public String test() {
        return "1";
    }
}
