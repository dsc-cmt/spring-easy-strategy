package com.cmt.springstrategy.test.function.common;

/**
 * @author jiangzihao
 * @date 2020-03-30
 **/
@Platform(PlatformEnum.TENCENT)
public class TencentStrategy implements PlatformStrategy {

    @Override
    public String hello() {
        return "腾讯";
    }
}
