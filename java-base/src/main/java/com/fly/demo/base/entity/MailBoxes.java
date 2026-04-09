package com.fly.demo.base.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MailBoxes {

    private static Map<String, List<EMail>> boxes = new ConcurrentHashMap<>();

    //生产者
    public void postMail(Map<String, List<EMail>> mails) {
        synchronized (this) {
            boxes.putAll(mails);
            notifyAll();
        }
    }

    //消费者
    public List<EMail> getMails(People people) {
        synchronized (this) {
            people.setStartWaitTime(System.currentTimeMillis());
            log.info("No.{} 开始取信...", people.getPeopleId());
            List<EMail> eMails = boxes.get(people.getPeopleId());
            long passTime = 0;
            while (eMails == null || eMails.size() == 0) {
                log.info("No.{} 没找到信, 再等等...", people.getPeopleId());
                long  waitTime = people.getTimeout() - passTime;
                if (waitTime <= 0) {
                    log.info("No.{} 等得不耐烦了, 放弃取信", people.getPeopleId());
                    break;
                }
                try {
                    wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - people.getStartWaitTime();
            }
            return eMails;
        }
    }

    public static Set<String> getIds() {
        return boxes.keySet();
    }
}
