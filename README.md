> 运行环境需要java8

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

## 引入
> 已上传到maven公库  
```
<dependency>
  <groupId>io.github.shengchaojie</groupId>
  <artifactId>spring-strategy-tool</artifactId>
  <version>1.2.0</version>
</dependency>
```

## 如何使用
### 基础使用
在这个框架中，每个接口的策略实现都会存在一个identifyCode对应，该identifyCode通过注解以及自定义逻辑生成

#### 0. 你的策略接口
```
public interface HelloStrategy {

    String hello();
}
```

#### 1. 实现自定义注解  

> 如果只有一个属性，直接使用自带的`@StrategyIdentifier`注解即可

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

> 注意@Component元注解


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

#### 2. 配置StrategyContainerFactoryBean

StrategyContainerFactoryBean配置有三个  

|参数|作用|
|---|---|
|strategyClass | 策略类  |
|strategyAnnotationClass|  策略注解类  |
|identifyCodeGetter | 从注解提取identifyCode的逻辑  |

```java
@Bean
public StrategyContainerFactoryBean<HelloStrategy, People> helloStrategyContainer(){
    StrategyContainerFactoryBean<HelloStrategy, People> factoryBean = new StrategyContainerFactoryBean<>();
    factoryBean.setStrategyClass(HelloStrategy.class);
    factoryBean.setStrategyAnnotationClass(People.class);
    factoryBean.setIdentifyCodeGetter(a -> Joiner.on(",").join(a.district(),a.gender().name()));
    return factoryBean;
}
```

> 目前来看不支持xml配置，因为有函数入参，都sb时代了，谁还xml

由下面的注入可以发现，Spring忽略了泛型，因此在StrategyContainerFactoryBean增加了一个build方法，简化配置
```
@Bean
public StrategyContainerFactoryBean helloStrategyContainer(){
    return StrategyContainerFactoryBean.build(HelloStrategy.class,People.class,a -> Joiner.on(",").join(a.district(),a.gender().name()));
}
```


#### 3. 注入StrategyContainer
```
/**
 * 需要注意，spring容器注入会把泛型丢掉，所以必须通过beanname注入
 * 或者@bean的方法名和下面的属性名helloStrategyContainer保持一致
 */
@Resource(name = "helloStrategyContainer")
private StrategyContainer<HelloStrategy> helloStrategyContainer;
```



#### 4. 使用
```
HelloStrategy helloStrategy = helloStrategyContainer.getStrategy(Joiner.on(",").join("chinese", GenderEnum.FEMALE.name()));
helloStrategy.hello()
```

> 这边getStrategy的入参需要和自定义注解生成的identifyCode相同
> 如果对于一个策略接口，有相同的identifyCode产生，在FactoryBean初始化的时候会报错

### 进阶功能
#### 1. 手动绑定策略

StrategyContainer接口提供一个register方法用于手动绑定identifyCode和策略
```
helloStrategyContainer.register(Joiner.on(",").join("american", GenderEnum.FEMALE.name()),()->{
    return "hello";
});
```

#### 2. 单策略支持多注解  
在业务开发中，很多场景下，对于多个identifyCode我们的策略是相同。  
在原有自定义注解的基础上进行改造，比如原有注解如下
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface One {

    String test();

}

```
我们新增一个容器注解，为了支持在同一类上标注多个相同注解
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Many {

    One[] value();
}
```
修改原有注解为
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Many.class)
@Component
public @interface One {

    String test();

}
```
这是java8提供的语法糖，在做如上配置后，你就能在一个策略类标注多个相同注解了  

框架自带的StrategyIdentifier已支持多注解模式


#### 3. 默认策略  
新增了一个`@DefaultStrategy`注解，策略类标注该注解后，如果通过identifyCode找不到策略实现，会执行默认策略逻辑。

> 注意: 一个接口默认策略只能有一个，不然会报错  

#### 4. 支持多策略模式
某些业务场景下，针对一个identifyCode可能需要多个策略执行，比如我们的校验逻辑，针对一个业务方，可能会触发多个。  
因此实现了MultiStrategyContainerFactoryBean类，基本使用和StrategyContainerFactoryBean类似。
也正是因为通过identifyCode可以获取到多个策略了，针对这种模式，新增了排序功能，可以使用spring的Order注解来指定顺序。

使用方式如下  
策略配置
```
@StrategyIdentifier(identifyCode = "1")
@StrategyIdentifier(identifyCode = "1")
@StrategyIdentifier(identifyCode = "3")
@Order(4)
public class AValidation implements Validation{
    @Override
    public void validate() {
        System.out.println("AAAAAA");
    }
}

@StrategyIdentifier(identifyCode = "1")
@StrategyIdentifier(identifyCode = "3")
@Order(3)
public class BValidation implements Validation{
    @Override
    public void validate() {
        System.out.println("BBBBBBB");
    }
}
```
框架配置
```
@Bean
public MultiStrategyContainerFactoryBean<Validation, StrategyIdentifier> validation(){
    return MultiStrategyContainerFactoryBean.build(Validation.class,StrategyIdentifier.class,a -> a.identifyCode());
}
```
注入
```
@Resource(name = "validation")
private MultiStrategyContainer<Validation> validationMultiStrategyContainer;
```
使用
```
List<Validation> strategies = validationMultiStrategyContainer.getStrategies("1");
```

## 更新
- 2019-09-19
    支持多策略模式
- 2019-09-10  
    支持单策略多注解  
    支持默认注解
- 2019-09-06   
    解决策略类生成Aop代理时获取不到策略的问题,增加Aop相关测试用例
- 2019-08-12   
    修改类名StrategyManager为StrategyContainer

> 完整使用案例示例见test用例