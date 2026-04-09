package com.fly.demo.base.entity.design_pattern.single;

/*
* 饿汉式方式二
* */
public class SingletonHungry2 {

    // 1. 私有构造方法
    private SingletonHungry2() {

    }

    // 2. 声明Singleton2类型的变量
    private static SingletonHungry2 singletonHungry2;

    //3. 静态代码块创建对象
    static {
        singletonHungry2 = new SingletonHungry2();
    }

    //4. 提供公共访问方法获取单例对象
    public static SingletonHungry2 getInstance() {
        return singletonHungry2;
    }
}
