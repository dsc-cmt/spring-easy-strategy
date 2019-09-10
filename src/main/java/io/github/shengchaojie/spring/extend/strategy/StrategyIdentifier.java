package io.github.shengchaojie.spring.extend.strategy;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 策略类注解
 * 如果策略标识符只有一个直接使用该注解就行
 * @author shengchaojie
 * @date 2019-07-30
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(StrategyIdentifiers.class)
@Component
public @interface StrategyIdentifier {

    /**
     * 策略唯一标识符
     * @return
     */
    String identifyCode();

}
