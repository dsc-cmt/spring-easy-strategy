package com.cmt.springstrategy.test.function;

import com.cmt.springstrategy.test.function.common.ChineseGirlHelloStrategy;
import com.cmt.springstrategy.test.function.common.HelloStrategy;
import com.cmt.springstrategy.test.function.common.JapanGirlHelloStrategy;
import com.cmt.springstrategy.test.function.common.People;
import com.cmt.springstrategy.test.function.repeatable.One;
import com.cmt.springstrategy.test.function.repeatable.RepeatableStrategy1;
import com.cmt.springstrategy.test.function.repeatable.RepeatableStrategy2;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.DefaultAdvisorChainFactory;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @author shengchaojie
 * @date 2019-09-06
 **/
public class SpringFeatureTest {

    @Test
    public void testCglibAop() {
        DefaultAopProxyFactory defaultAopProxyFactory = new DefaultAopProxyFactory();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setInterfaces(HelloStrategy.class);
        advisedSupport.setTarget(new ChineseGirlHelloStrategy());
        advisedSupport.setAdvisorChainFactory(new DefaultAdvisorChainFactory());
        advisedSupport.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("test");
            }
        });
        advisedSupport.setProxyTargetClass(true);

        HelloStrategy helloStrategy = (HelloStrategy) defaultAopProxyFactory.createAopProxy(advisedSupport).getProxy();
        Assert.assertTrue(AopUtils.isAopProxy(helloStrategy));
        Assert.assertEquals(AopUtils.getTargetClass(helloStrategy),ChineseGirlHelloStrategy.class);
    }

    @Test
    public void testJDKAop(){
        DefaultAopProxyFactory defaultAopProxyFactory = new DefaultAopProxyFactory();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setInterfaces(HelloStrategy.class);
        advisedSupport.setTarget(new ChineseGirlHelloStrategy());
        advisedSupport.setAdvisorChainFactory(new DefaultAdvisorChainFactory());
        advisedSupport.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("test");
            }
        });
        advisedSupport.setProxyTargetClass(false);

        HelloStrategy helloStrategy = (HelloStrategy) defaultAopProxyFactory.createAopProxy(advisedSupport).getProxy();
        Assert.assertTrue(AopUtils.isAopProxy(helloStrategy));
        Assert.assertEquals(AopUtils.getTargetClass(helloStrategy),ChineseGirlHelloStrategy.class);
    }

    /**
     * AnnotationUtils.getRepeatableAnnotations兼容了Repeatable模式和非Repeatable模式
     */
    @Test
    public void testSpringAnnotationUtil(){
        Assert.assertEquals(AnnotationUtils.getRepeatableAnnotations(RepeatableStrategy1.class, One.class).size(),2);
        Assert.assertTrue(AnnotationUtils.getAnnotation(RepeatableStrategy1.class, One.class)==null);
        Assert.assertEquals(AnnotationUtils.getRepeatableAnnotations(RepeatableStrategy2.class, One.class).size(),1);
        Assert.assertTrue(AnnotationUtils.getAnnotation(RepeatableStrategy2.class, One.class)!=null);

        Assert.assertEquals(AnnotationUtils.getRepeatableAnnotations(JapanGirlHelloStrategy.class, People.class).size(),1);
    }

}
