package io.github.shengchaojie.spring.extend.strategy;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 策略类注解
 * 如果策略标识符只有一个直接使用该注解就行
 * @author shengchaojie
 * @date 2019-07-30
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface StrategyIdentifier {

    /**
     * 策略唯一标识符
     * @return
     */
    String identifyCode();

}
