package io.github.cmt.springstrategy.test.function.common;

/**
 * @author jiangzihao
 * @date 2020-03-30
 **/
@Platform(PlatformEnum.BAI_DU)
public class BaiDuStrategy implements PlatformStrategy {

    @Override
    public String hello() {
        return "百度";
    }
}
