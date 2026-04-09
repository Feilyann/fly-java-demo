package com.fly.demo.base;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        log.info("compare result: {}","file".compareTo("folder"));
    }

}