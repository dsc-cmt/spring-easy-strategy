package io.github.springstrategy.test.demo.calculateprice;

/**
 * 计算价格策略
 * @author shengchaojie
 * @date 2019-07-30
 **/
public interface CalculatePriceStrategy {

    /**
     * 根据sku以及购买数量计算价格
     * @param sku
     * @param num
     * @return
     */
    Integer calculate(String sku, Integer unitPrice, Integer num);

}
