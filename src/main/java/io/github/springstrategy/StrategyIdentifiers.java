package io.github.springstrategy;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来解决有时候不同策略对应相同逻辑的情况
 * @author shengchaojie
 * @date 2019-09-10
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface StrategyIdentifiers {

    StrategyIdentifier[] value();

}
