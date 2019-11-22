package io.github.springstrategy.test.demo;

import io.github.springstrategy.test.demo.service.CreateOrderRequest;
import io.github.springstrategy.test.demo.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class StrategyTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAssembleStrategy(){
        orderService.createOrder(CreateOrderRequest.builder()
                .sku("F1233")
                .num(3)
                .userId(1234L)
                .userLevel(UserLevel.MEDIUM)
                .build());

        orderService.createOrder(CreateOrderRequest.builder()
                .sku("F1233")
                .num(3)
                .userId(1234L)
                .userLevel(UserLevel.HIGH)
                .build());

        orderService.createOrder(CreateOrderRequest.builder()
                .sku("V1233")
                .num(3)
                .userId(1234L)
                .userLevel(UserLevel.HIGH)
                .build());
    }


}
