package com.fly.demo.base.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class Father {

    public int id;

    private String name;

    private int age;

    public void play(HashMap<String, String> map) {
        log.info("爸爸正在{}", map.get("father"));
    }

}
