package io.github.cmt.easystrategy.starter.test.function;

import io.github.cmt.easystrategy.starter.EasyStrategy;
import io.github.cmt.easystrategy.starter.StrategyContainer;
import io.github.cmt.easystrategy.starter.test.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shengchaojie
 * @date 2020/12/6
 **/
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestService {

    @EasyStrategy
    StrategyContainer<StrategyI> strategyContainer;

    @EasyStrategy
    StrategyContainer<StrategyI> strategyContainerSample;

    @EasyStrategy
    io.github.cmt.easystrategy.StrategyContainer<String,StrategyI> strategyContainer2;

    @Test
    public void testCache(){
        Assert.assertEquals(strategyContainer,strategyContainerSample);
    }

    @Test
    public void test111(){
        Assert.assertEquals(strategyContainer.getStrategy("A").test(),"A");
        Assert.assertEquals(strategyContainer.getStrategy("B").test(),"B");
        Assert.assertEquals(strategyContainer2.getStrategy("B").test(),"B");
        Assert.assertEquals(strategyContainer2.getStrategy("B").test(),"B");
    }

}
