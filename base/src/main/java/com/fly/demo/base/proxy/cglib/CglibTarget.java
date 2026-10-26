package com.fly.demo.base.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CglibTarget {

    public void save() {
        log.info("save running");
    }
}
