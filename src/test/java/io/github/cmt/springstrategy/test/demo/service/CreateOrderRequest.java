package io.github.cmt.springstrategy.test.demo.service;

import io.github.cmt.springstrategy.test.demo.UserLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    private String sku;

    private Integer num;

    private Long userId;

    private UserLevel userLevel;

}
