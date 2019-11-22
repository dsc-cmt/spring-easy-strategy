package io.github.shengchaojie.springstrategy.test.function.multi;

import io.github.shengchaojie.springstrategy.StrategyIdentifier;
import org.springframework.core.annotation.Order;

/**
 * @author shengchaojie
 * @date 2019-09-19
 **/
@StrategyIdentifier(identifyCode = "1")
@StrategyIdentifier(identifyCode = "3")
@Order(3)
public class BValidation implements Validation{
    @Override
    public void validate() {
        System.out.println("BBBBBBB");
    }
}
