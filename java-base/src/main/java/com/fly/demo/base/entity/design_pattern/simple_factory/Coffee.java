package com.fly.demo.base.entity.design_pattern.simple_factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Coffee {

    public abstract String getName();

    public void addSugar() {
        log.info("加糖");
    }

    public void addMilk() {
        log.info("加奶");
    }
}
