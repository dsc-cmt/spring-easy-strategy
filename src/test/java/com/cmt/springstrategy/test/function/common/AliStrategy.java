package com.cmt.springstrategy.test.function.common;

/**
 * @author jiangzihao
 * @date 2020-03-30
 **/
@Platform(PlatformEnum.ALI)
public class AliStrategy implements PlatformStrategy {

    @Override
    public String hello() {
        return "阿里巴巴";
    }
}
