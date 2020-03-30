package io.github.cmt.easystrategy.test.function.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface People {

    String district();

    GenderEnum gender();

}
