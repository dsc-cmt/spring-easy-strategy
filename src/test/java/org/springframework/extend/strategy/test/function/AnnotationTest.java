package org.springframework.extend.strategy.test.function;

import com.google.common.base.Joiner;
import org.springframework.extend.strategy.StrategyManager;
import org.springframework.extend.strategy.test.function.common.GenderEnum;
import org.springframework.extend.strategy.test.function.common.HelloStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class AnnotationTest {

    /**
     * 需要注意，spring容器注入会把泛型丢掉，所以必须通过beanname注入
     */
    @Resource(name = "helloStrategyManager")
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
