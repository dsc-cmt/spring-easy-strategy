package io.github.shengchaojie.spring.extend.strategy.test.function.multi;

import io.github.shengchaojie.spring.extend.strategy.StrategyIdentifier;
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
