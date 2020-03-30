package io.github.cmt.springstrategy.test.function.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author shengchaojie
 * @date 2019-09-06
 **/
public class TestAdvice implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("test");
    }
}
