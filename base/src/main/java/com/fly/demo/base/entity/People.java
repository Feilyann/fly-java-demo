package com.fly.demo.base.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*
* 居民, 收信人, 一个居民可以拥有多封信
* */
@Slf4j
@Data
public class People implements Runnable {

    private MailBoxes mailBoxes;

    private String peopleId;

    private int postmanId;

    private String name;

    private List<EMail> mails;

    private long startWaitTime;

    private long timeout = 10000;

    public People() {

    }

    public People(MailBoxes mailBoxes) {
        this.mailBoxes = mailBoxes;
    }

    @Override
    public void run() {
        while (peopleId == null || peopleId.length() == 0) {
            log.info("还没收到快递通知...");
        }
        log.info("尊敬的 No.{} 顾客, 快递员: {} 正在为您派件...", peopleId, postmanId);
        log.info("No.{} 顾客 准备收信...", peopleId);
        mails = mailBoxes.getMails(this);
        if (mails == null || mails.size() == 0) {
            log.info("No.{} 顾客没有可收信件", peopleId);
        } else {
            for (EMail mail : mails) {
                if (mail != null) {
                    if (mail.getPeopleId() != this.peopleId) {
                        log.error("收错信了: {}", mail);
                    } else {
                        log.info("收到信了, 信件Id: {}, 信件内容: {}", mail.getEmailId(), mail.getContent());
                    }
                } else {
                    log.info("No.{} 顾客没有可收信件", peopleId);
                }
            }
        }
    }

    public void setPeopleId(String id) {
        this.peopleId = id;
    }

}
