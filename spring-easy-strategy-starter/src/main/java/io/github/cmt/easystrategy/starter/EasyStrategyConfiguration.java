package io.github.cmt.easystrategy.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shengchaojie
 * @date 2020/12/6
 **/
@Configuration
public class EasyStrategyConfiguration {

    @Bean
    public StrategyAnnotationBeanPostProcessor strategyAnnotationBeanPostProcessor(){
        return new StrategyAnnotationBeanPostProcessor();
    }

}
