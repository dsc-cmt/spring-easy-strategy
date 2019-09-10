package io.github.shengchaojie.spring.extend.strategy.test.function;

import io.github.shengchaojie.spring.extend.strategy.StrategyContainer;
import io.github.shengchaojie.spring.extend.strategy.StrategyContainerFactoryBean;
import io.github.shengchaojie.spring.extend.strategy.exceptions.StrategyDuplicateException;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.One;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.RepeatableStrategy;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.RepeatableStrategy1;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author shengchaojie
 * @date 2019-09-10
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class RepeatableStrategyTest {

    @Resource(name = "repeatableStrategyManager")
    StrategyContainer<RepeatableStrategy> repeatableStrategyContainer;


    @Test
    public void testRepeatable(){
        Assert.assertEquals(repeatableStrategyContainer.getStrategy("1").test(),"1");
        Assert.assertEquals(repeatableStrategyContainer.getStrategy("3").test(),"1");
        Assert.assertEquals(repeatableStrategyContainer.getStrategy("2").test(),"2");
    }

    @Test
    public void testMultiError() throws Exception {
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
            Assert.assertTrue(ex instanceof StrategyDuplicateException);
        }

    }

}
