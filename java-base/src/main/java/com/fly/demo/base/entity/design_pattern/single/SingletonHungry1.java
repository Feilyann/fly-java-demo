package com.fly.demo.base.entity.design_pattern.single;

/*
 * 饿汉式方式一: 静态成员变量, 在类加载时就会将该类中的对象创建好
 * */
public class SingletonHungry1 {

    //1. 私有构造方法
    private SingletonHungry1() {

    }

    //2. 在本类中创建该类的对象
    private static SingletonHungry1 instance = new SingletonHungry1();

    //3. 提供一个公共的访问方式
    public static SingletonHungry1 getInstance() {
        return instance;
    }

}
