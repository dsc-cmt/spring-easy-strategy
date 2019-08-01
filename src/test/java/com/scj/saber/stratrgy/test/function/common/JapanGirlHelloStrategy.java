package com.scj.saber.stratrgy.test.function.common;

/**
 * @author shengchaojie
 * @date 2019-08-01
 **/
@People(district = "japan",gender = GenderEnum.FEMALE)
public class JapanGirlHelloStrategy implements HelloStrategy{
    @Override
    public String hello() {
        return "ohayo";
    }
}
