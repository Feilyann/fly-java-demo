package com.fly.demo.base.entity;

import lombok.Data;

@Data
public class ThreadDemo {

    private int num;

    public void sum() {
        synchronized (this) {
            num++;
        }
    }
}
