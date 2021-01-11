package io.github.cmt.easystrategy.test.function.common;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@People(district = "chinese",gender = GenderEnum.FEMALE)
public class ChineseGirlHelloStrategy implements HelloStrategy {
    @Override
    public String hello() {
        return "你好";
    }
}
