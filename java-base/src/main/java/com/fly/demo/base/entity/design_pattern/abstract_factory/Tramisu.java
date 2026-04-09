package com.fly.demo.base.entity.design_pattern.abstract_factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tramisu extends Dessert {
    @Override
    public void show() {
        log.info("提拉米苏");
    }
}
