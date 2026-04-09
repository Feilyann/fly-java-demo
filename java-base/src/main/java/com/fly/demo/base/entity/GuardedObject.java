package com.fly.demo.base.entity;

import lombok.Data;


@Data
public class GuardedObject {


    //结果1
    private Object response;

    //获取结果
    public Object getResponse(long timeout) {
        synchronized (this) {
            long start = System.currentTimeMillis();
            long passTime = 0;
            while (response == null) {
                long waitTime = timeout - passTime;
                if (waitTime <= 0) {
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - start;
            }
            return response;
        }
    }

    //产生结果
    public void product(Object response) {
        synchronized (this) {
            this.response = response;
            this.notify();
        }
    }
}
