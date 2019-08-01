package org.springframework.extend.stratrgy.test.function;

import com.google.common.base.Joiner;
import org.springframework.extend.strategy.StrategyManager;
import org.springframework.extend.stratrgy.test.function.common.GenderEnum;
import org.springframework.extend.stratrgy.test.function.common.HelloStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class AnnotationTest {

    @Autowired
    private StrategyManager<HelloStrategy> helloStrategyManager;

    @Test
    public void test(){
        HelloStrategy helloStrategy = helloStrategyManager.getStrategy(Joiner.on(",").join("chinese", GenderEnum.FEMALE.name()));
        Assert.assertEquals("你好",helloStrategy.hello());

        helloStrategy = helloStrategyManager.getStrategy(Joiner.on(",").join("japan", GenderEnum.FEMALE.name()));
        Assert.assertEquals("ohayo",helloStrategy.hello());

        helloStrategy = helloStrategyManager.getStrategy(Joiner.on(",").join("american", GenderEnum.FEMALE.name()));
        Assert.assertNull(helloStrategy);

        helloStrategyManager.register(Joiner.on(",").join("american", GenderEnum.FEMALE.name()),()->{
            return "hello";
        });

        helloStrategy = helloStrategyManager.getStrategy(Joiner.on(",").join("american", GenderEnum.FEMALE.name()));
        Assert.assertNotNull(helloStrategy);
        Assert.assertEquals("hello",helloStrategy.hello());
    }

}
