package io.github.cmt.springstrategy.test.function.multi;

import io.github.cmt.springstrategy.StrategyIdentifier;
import org.springframework.core.annotation.Order;

/**
 * @author shengchaojie
 * @date 2019-09-19
 **/
@StrategyIdentifier(identifyCode = "1")
@StrategyIdentifier(identifyCode = "1")
@StrategyIdentifier(identifyCode = "3")
@Order(4)
public class AValidation implements Validation{
    @Override
    public void validate() {
        System.out.println("AAAAAA");
    }
}
