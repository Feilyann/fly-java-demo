package com.fly.demo.base.pattern;

public class Singleton {

    //饿汉式
    private static Singleton SINGLETON = new Singleton();

    public static Singleton getSingleton() {
        return SINGLETON;
    }

    //懒汉式
    private volatile static Singleton SINGLETON2;

    public static Singleton getSINGLETON2() {
        if (SINGLETON2 == null) {
            synchronized (Singleton.class){
                if (SINGLETON2 == null) {
                    SINGLETON2 = new Singleton();
                }
            }
        }
        return SINGLETON2;
    }
}
