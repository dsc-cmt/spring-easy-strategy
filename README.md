## 介绍
spring-strategy-tool 是一个在spring环境下方便进行策略模式开发的工具类。  
在业务开发使用策略模式中，发现策略的注册以及获取逻辑是一个重复的功能，所以设计该工具类方便开发。  
主要原理是利用spring的FactoryBean针对每个接口T策略生成一个StrategyManger\<T> Bean。  

## 策略模式？
策略模式绝对是我们写业务代码中用到最多的设计模式之一。  
举个简单的例子  
比如现在我们针对不同的商品类型，有不同的计算价格方式  
在不使用设计模式的情况下，肯定存在以下代码
```
if(type='水果'){
    //计算价格
}else if(type='蔬菜'){
    //另一种计算价格模式
}else{
    ...
}
```
在使用策略模式后，我们的代码变成这样
```
strategy = strategyManger.get('水果');
price = strategy.calculate(...)
```
有什么好处？

1. 减少if/else，让我们的逻辑更加内聚
2. 符合开闭原则，新增逻辑时，主流程代码不需要改动，只要增加新的策略即可


## 如何使用
在这个框架中，每个接口的策略实现都会存在一个identifyCode对应，该identifyCode通过注解以及自定义逻辑生成

0. 你的策略接口
```
public interface HelloStrategy {

    String hello();
}
```

1. 实现自定义注解  
考虑到有些策略是通过多个属性进行匹配的，所以这边支持自定义注解绑定策略
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface People {

    String district();

    GenderEnum gender();

}
```

将该注解注解到策略实现类上去
```
@People(district = "chinese",gender = GenderEnum.FEMALE)
public class ChineseGirlHelloStrategy implements HelloStrategy {
    @Override
    public String hello() {
        return "你好";
    }
}
```

如果只有一个属性，直接使用自带的StrategyIdentifier注解即可

2. 配置FactoryBean
```
@Bean
public StrategyManagerFactoryBean<HelloStrategy, People> helloStrategyManager(){
    StrategyManagerFactoryBean<HelloStrategy, People> factoryBean = new StrategyManagerFactoryBean<>();
    factoryBean.setStrategyClass(HelloStrategy.class);
    factoryBean.setStrategyAnnotationClass(People.class);
    factoryBean.setIdentifyCodeGetter(a -> Joiner.on(",").join(a.district(),a.gender().name()));
    return factoryBean;
}
```

> 目前来看不支持xml配置，因为有泛型以及函数入参，都sb时代了，谁还xml

StrategyManagerFactoryBean配置有三个  

|参数|作用|
|---|---|
|strategyClass | 策略类  |
|strategyAnnotationClass|  策略注解类  |
|identifyCodeGetter | 从注解提取identifyCode的逻辑  |


3. 注入StrategyManager
```
/**
 * 需要注意，spring容器注入会把泛型丢掉，所以必须通过beanname注入
 * 或者@bean的方法名和下面的属性名helloStrategyManager保持一致
 */
@Resource(name = "helloStrategyManager")
private StrategyManager<HelloStrategy> helloStrategyManager;
```



4. 使用
```
HelloStrategy helloStrategy = helloStrategyManager.getStrategy(Joiner.on(",").join("chinese", GenderEnum.FEMALE.name()));
helloStrategy.hello()
```

5. 手动绑定策略
StrategyManager接口有一个register方法用于手动绑定identifyCode和策略
```
helloStrategyManager.register(Joiner.on(",").join("american", GenderEnum.FEMALE.name()),()->{
    return "hello";
});
```

> 完整示例见test
