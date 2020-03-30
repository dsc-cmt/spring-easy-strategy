package io.github.cmt.springstrategy.test.function.common;

/**
 * @author jiangzihao
 * @date 2020-03-30
 **/
@Platform(PlatformEnum.ALI)//标记注解 完成策略实现类与标识符的绑定
public class AliStrategy implements PlatformStrategy {

    @Override
    public String hello() {
        return "阿里巴巴";
    }
}
