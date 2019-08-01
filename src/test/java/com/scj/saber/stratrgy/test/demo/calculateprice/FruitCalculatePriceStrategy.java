package com.scj.saber.stratrgy.test.demo.calculateprice;

import com.scj.saber.strategy.StrategyIdentifier;

/**
 * @author shengchaojie
 * @date 2019-07-30
 **/
@StrategyIdentifier(identifyCode = "FRUIT")
public class FruitCalculatePriceStrategy implements CalculatePriceStrategy{

    public Integer calculate(String sku, Integer unitPrice, Integer num) {
        System.out.println("满2件，打8折");
        if(num <2){
            return unitPrice*num;
        }else{
            return unitPrice*num*8/10;
        }
    }
}
