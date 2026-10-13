package com.fly.demo.base.test.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CglibAdvance {

    public static void before() {
        log.info("before 前置增强...");
    }

    public static void after() {
        log.info("after 后置增强...");
    }
}
