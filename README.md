## 介绍
spring-strategy-tool 是一个在spring环境下方便进行策略模式开发的工具类。  
在业务开发使用策略模式中，发现策略的注册以及获取逻辑是一个重复的功能，所以设计该工具类方便开发。  
主要原理是利用spring的FactoryBean针对每个接口策略生成一个StrategyManger Bean。  

## 如何使用
在这个框架中，每个接口的策略实现都会存在一个identifyCode对应，该identifyCode通过注解以及自定义逻辑生成

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
StrategyManagerFactoryBean配置有三个  

|||
|---|---|
|strategyClass | 策略类  |
|strategyAnnotationClass|  策略注解类  |
|identifyCodeGetter | 从注解提取identifyCode的逻辑  |


3. 注入StrategyManager
```
@Autowired
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

## todo
- 考虑将手动绑定单独抽出一个独立的FactoryBean出来，不想和注解模式耦合