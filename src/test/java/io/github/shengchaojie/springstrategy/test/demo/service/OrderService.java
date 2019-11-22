package io.github.shengchaojie.springstrategy.test.demo.service;

import io.github.shengchaojie.springstrategy.test.demo.calculateprice.CalculatePriceStrategy;
import io.github.shengchaojie.springstrategy.StrategyContainer;
import io.github.shengchaojie.springstrategy.test.demo.ProductTypeEnum;
import io.github.shengchaojie.springstrategy.test.demo.rewardpoints.PointsRewardStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author shengchaojie
 * @date 2019-07-31
 **/
@Service
public class OrderService {

    /**
     * 需要注意，spring容器注入会把泛型丢掉，所以必须通过beanname注入
     */
    @Resource(name = "calculatePriceStrategyManager2")
    private StrategyContainer<CalculatePriceStrategy> calculatePriceStrategyContainer;


    @Resource(name = "pointsRewardStrategyManager3")
    private StrategyContainer<PointsRewardStrategy> pointsRewardStrategyContainer;

    /**
     * 创建订单
     * @return
     */
    public void createOrder(CreateOrderRequest createOrderRequest){
        //创建订单实体
        //计算价格
        CalculatePriceStrategy calculatePriceStrategy = calculatePriceStrategyContainer.getStrategy(getSkuProductType(createOrderRequest.getSku()));
        Integer originPrice = getSkuUnitPrice(createOrderRequest.getSku());
        System.out.println("原价"+originPrice/100.0f+"购买件数"+createOrderRequest.getNum());
        Integer price = calculatePriceStrategy.calculate(createOrderRequest.getSku(),originPrice,createOrderRequest.getNum());
        System.out.println("下单价格为"+price/100.0f);
        //计算积分
        PointsRewardStrategy pointsRewardStrategy = pointsRewardStrategyContainer.getStrategy(createOrderRequest.getUserLevel().name());
        Integer points = pointsRewardStrategy.rewardPoints(price);
        System.out.println("奖励积分"+points);

        //下单完成
        System.out.println("下单完成");
    }

    /**
     * 根据sku决定产品类型
     * @param sku
     * @return
     */
    private String getSkuProductType(String sku){
        if(sku.startsWith("F")){
            return ProductTypeEnum.FRUIT.name();
        }else if(sku.startsWith("V")){
            return ProductTypeEnum.VEGETABLE.name();
        }else{
            throw new RuntimeException("暂不支持该SKU");
        }
    }

    /**
     * 单价在100-200内波动
     * @param sku
     * @return
     */
    private Integer getSkuUnitPrice(String sku){
        return ThreadLocalRandom.current().nextInt(1000,2000) *100;
    }

}
