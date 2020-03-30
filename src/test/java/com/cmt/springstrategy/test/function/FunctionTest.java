package com.cmt.springstrategy.test.function;

import com.cmt.springstrategy.MultiStrategyContainer;
import com.cmt.springstrategy.StrategyContainer;
import com.cmt.springstrategy.StrategyContainerFactoryBean;
import com.cmt.springstrategy.exceptions.StrategyException;
import com.cmt.springstrategy.test.function.common.GenderEnum;
import com.cmt.springstrategy.test.function.common.HelloStrategy;
import com.cmt.springstrategy.test.function.multi.*;
import com.cmt.springstrategy.test.function.repeatable.One;
import com.cmt.springstrategy.test.function.repeatable.RepeatableStrategy;
import com.cmt.springstrategy.test.function.repeatable.RepeatableStrategy1;
import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class FunctionTest {

    /**
     * 需要注意，spring容器注入会把泛型丢掉，所以必须通过beanname注入
     */
    @Resource(name = "helloStrategyManager")
    private StrategyContainer<HelloStrategy> helloStrategyContainer;

    @Resource(name = "repeatableStrategyManager")
    private StrategyContainer<RepeatableStrategy> repeatableStrategyContainer;

    @Resource(name = "validation")
    private MultiStrategyContainer<Validation> validationMultiStrategyContainer;

    @Test
    public void testBasicGetStrategy(){
        HelloStrategy helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("chinese", GenderEnum.FEMALE.name()));
        Assert.assertEquals("你好",helloStrategy.hello());

        helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("japan", GenderEnum.FEMALE.name()));
        Assert.assertEquals("ohayo",helloStrategy.hello());
    }

    @Test
    public void testProgrammaticAddStrategy(){
        helloStrategyContainer.register(Joiner.on(",").join("american", GenderEnum.FEMALE.name()),()->{
            return "custom";
        });

        HelloStrategy helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("american", GenderEnum.FEMALE.name()));
        Assert.assertNotNull(helloStrategy);
        Assert.assertEquals("custom",helloStrategy.hello());
    }

    @Test
    public void testDefaultStrategy(){
        HelloStrategy helloStrategy = helloStrategyContainer.getStrategy("2324234234324");
        Assert.assertNotNull(helloStrategy);
        Assert.assertEquals("default",helloStrategy.hello());
    }

    @Test
    public void testRepeatable(){
        Assert.assertEquals(repeatableStrategyContainer.getStrategy("1").test(),"1");
        Assert.assertEquals(repeatableStrategyContainer.getStrategy("3").test(),"1");
        Assert.assertEquals(repeatableStrategyContainer.getStrategy("2").test(),"2");
    }

    @Test
    public void testMultiStrategyThrowError() throws Exception {
        ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);

        Mockito.when(applicationContext.getBeanNamesForType(RepeatableStrategy.class)).thenReturn(new String[]{"1","2","3"});
        Mockito.when(applicationContext.getBean("1",RepeatableStrategy.class)).thenReturn(new RepeatableStrategy1());
        Mockito.when(applicationContext.getBean("2",RepeatableStrategy.class)).thenReturn(new RepeatableStrategy1());
        Mockito.when(applicationContext.getBean("3",RepeatableStrategy.class)).thenReturn(new RepeatableStrategy1());

        try {
            StrategyContainerFactoryBean<RepeatableStrategy, One> factoryBean = new StrategyContainerFactoryBean<>();
            factoryBean.setStrategyClass(RepeatableStrategy.class);
            factoryBean.setStrategyAnnotationClass(One.class);
            factoryBean.setIdentifyCodeGetter(a->a.test());
            factoryBean.setApplicationContext(applicationContext);
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof StrategyException);
        }

    }

    @Test
    public void testGetOrderedMultiStrategy(){
        List<Validation> strategies = validationMultiStrategyContainer.getStrategies("1");
        Assert.assertEquals(2,strategies.size());
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(0)).isAssignableFrom(BValidation.class));
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(1)).isAssignableFrom(AValidation.class));
        strategies = validationMultiStrategyContainer.getStrategies("2");
        Assert.assertEquals(2,strategies.size());
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(0)).isAssignableFrom(DValidation.class));
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(1)).isAssignableFrom(CValidation.class));
        strategies = validationMultiStrategyContainer.getStrategies("3");
        Assert.assertEquals(4,strategies.size());
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(0)).isAssignableFrom(DValidation.class));
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(1)).isAssignableFrom(CValidation.class));
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(2)).isAssignableFrom(BValidation.class));
        Assert.assertTrue(AopUtils.getTargetClass(strategies.get(3)).isAssignableFrom(AValidation.class));
    }

}
