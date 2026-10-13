package com.fly.demo.base.entity.design_pattern.single;

/*
* 懒汉式: 只有在调用获取方法时, 才会创建单例实例
* 存在线程安全问题
* */
public class SingletonLazy {

    //1. 私有构造方法
    private SingletonLazy() {}

    //2. 声明singletonlazy类型的变量instance
    private static SingletonLazy singletonLazy;

    //3. 对外提供公共方法创建获取实例
    public static synchronized SingletonLazy getInstance() {

        //双重检查锁机制, 第一次判断singletonLazy不为null, 就不需要抢占锁了, 直接返回就好, 这样可以节省cpu 的性能损耗
        if (singletonLazy == null) {
            synchronized (SingletonLazy.class) {
                if (singletonLazy == null)
                    singletonLazy = new SingletonLazy();
            }
        }
        return singletonLazy;
    }
}
