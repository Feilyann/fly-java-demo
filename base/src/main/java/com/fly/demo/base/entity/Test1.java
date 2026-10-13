package com.fly.demo.base.entity;

public class Test1<T> {

    public Test1(T data) {

    }

    public Test1<T> instance(T t) {
        return new Test1<>(t);
    }
}
