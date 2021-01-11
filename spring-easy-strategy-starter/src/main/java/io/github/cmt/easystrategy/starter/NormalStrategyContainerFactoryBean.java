package io.github.cmt.easystrategy.starter;

import io.github.cmt.easystrategy.StrategyContainerFactoryBean;
import io.github.cmt.easystrategy.StrategyIdentifier;
import org.springframework.util.Assert;

/**
 * @author shengchaojie
 * @date 2020/11/21
 **/
public class NormalStrategyContainerFactoryBean<T> extends StrategyContainerFactoryBean<T, StrategyIdentifier, String> {

    public NormalStrategyContainerFactoryBean(Class<T> strategyClass) {
        super(strategyClass,StrategyIdentifier.class, StrategyIdentifier::identifyCode);
    }


    @Override
    public StrategyContainer<T> getObject() throws Exception {
        Assert.notNull(strategyClass, "strategyClass must not be null");
        Assert.notNull(strategyAnnotationClass, "strategyAnnotationClass must not be null");
        Assert.notNull(identifierGetter, "identifierGetter must not be null");

        return new StrategyContainer<T>() {
            @Override
            public T getStrategy(String identify) {
                return getStrategyFromContainer(identify);
            }

            @Override
            public void register(String identify, T strategy) {
                strategyTable.put(identify, strategy);
            }

        };
    }
}
