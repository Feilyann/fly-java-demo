package com.fly.demo.base.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Son extends Father{

    private String selfName;

    public void test() {

    }

    public void test(int index) {

    }

    public int test(int index, String str) {
        return 0;
    }

    public void play(Map<String, String> map) {
        log.info("儿子正在{}", map.get("son"));
    }

}
