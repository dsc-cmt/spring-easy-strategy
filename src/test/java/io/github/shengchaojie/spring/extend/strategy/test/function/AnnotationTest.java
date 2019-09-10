package io.github.shengchaojie.spring.extend.strategy.test.function;

import com.google.common.base.Joiner;
import io.github.shengchaojie.spring.extend.strategy.StrategyContainer;
import io.github.shengchaojie.spring.extend.strategy.test.function.common.GenderEnum;
import io.github.shengchaojie.spring.extend.strategy.test.function.common.HelloStrategy;
import io.github.shengchaojie.spring.extend.strategy.test.function.common.JapanGirlHelloStrategy;
import io.github.shengchaojie.spring.extend.strategy.test.function.common.People;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.One;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.RepeatableStrategy1;
import io.github.shengchaojie.spring.extend.strategy.test.function.repeatable.RepeatableStrategy2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.annotation.AnnotationUtils;
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
    private StrategyContainer<HelloStrategy> helloStrategyContainer;

    @Test
    public void test(){
        HelloStrategy helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("chinese", GenderEnum.FEMALE.name()));
        Assert.assertEquals("你好",helloStrategy.hello());

        helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("japan", GenderEnum.FEMALE.name()));
        Assert.assertEquals("ohayo",helloStrategy.hello());

        helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("american", GenderEnum.FEMALE.name()));
        Assert.assertNull(helloStrategy);

        helloStrategyContainer.register(Joiner.on(",").join("american", GenderEnum.FEMALE.name()),()->{
            return "hello";
        });

        helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("american", GenderEnum.FEMALE.name()));
        Assert.assertNotNull(helloStrategy);
        Assert.assertEquals("hello",helloStrategy.hello());
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
