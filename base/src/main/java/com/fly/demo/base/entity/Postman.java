package com.fly.demo.base.entity;

import com.fly.demo.base.util.AlgorithmUtil;
import com.fly.demo.base.util.MathUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/*
* 邮递员
* */
@Slf4j
@Data
public class Postman implements Runnable {

    private MailBoxes mailBoxes;

    private int postManId;

    private int id = 1;

    private List<People> peoples;

    public Postman(MailBoxes mailBoxes, int postManId, List<People> peoples) {
        this.mailBoxes = mailBoxes;
        this.postManId = postManId;
        this.peoples = peoples;
    }

    @Override
    public void run() {
        Map<String, List<EMail>> mails = startSendMails();
        mailBoxes.postMail(mails);
    }

    private String generateId() {
        return postManId + "-" + id++;
    }

    //为每个人分配信件
    private static List<EMail> allotEmailsForPeople(String peopleId) {
        //每个人有随机封信
        int random = MathUtil.random(1, 10);
        List<EMail> eMails = new ArrayList<>();
        for (int i = 0; i < random; i++) {
            EMail eMail = new EMail();
            eMail.setPeopleId(peopleId);
            eMail.setEmailId(i + 1);
            eMail.setContent("居民 " + eMail.getPeopleId() + " 的第" + eMail.getEmailId() + " 封信");
            eMails.add(eMail);
        }
        return eMails;
    }

    //开始送信
    private Map<String, List<EMail>> startSendMails() {
        Map<String, List<EMail>> mails= new HashMap<>();
        for (People people : peoples) {
            people.setPeopleId(generateId());
            List<EMail> eMails = allotEmailsForPeople(people.getPeopleId());
            mails.put(people.getPeopleId(), eMails);
        }
        return mails;
    }
}
