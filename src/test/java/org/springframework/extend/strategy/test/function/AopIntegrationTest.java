package org.springframework.extend.strategy.test.function;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.DefaultAdvisorChainFactory;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.extend.strategy.test.function.common.ChineseGirlHelloStrategy;
import org.springframework.extend.strategy.test.function.common.HelloStrategy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

/**
 * @author shengchaojie
 * @date 2019-09-06
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class AopIntegrationTest {

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

}
