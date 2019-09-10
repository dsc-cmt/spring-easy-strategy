package io.github.shengchaojie.spring.extend.strategy.test.function;

import io.github.shengchaojie.spring.extend.strategy.StrategyContainer;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.RepeatableStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

}
