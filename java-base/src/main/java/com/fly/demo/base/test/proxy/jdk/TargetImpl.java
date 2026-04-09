package com.fly.demo.base.test.proxy.jdk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TargetImpl implements Target {

    @Override
    public String save(String data) {
        System.out.println(data);
        return data;
    }
}
