package io.github.cmt.easystrategy.test.function.repeatable;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Many.class)
@Component
public @interface One {

    String test();

}
