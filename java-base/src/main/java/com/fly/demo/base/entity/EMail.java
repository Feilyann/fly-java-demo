package com.fly.demo.base.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*
* 信件
* */
@Slf4j
@Data
public class EMail {

    private String peopleId;

    private int emailId;

    private String content;
}
