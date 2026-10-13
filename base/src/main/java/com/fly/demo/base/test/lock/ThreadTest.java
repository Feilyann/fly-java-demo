package com.fly.demo.base.test.lock;

import com.fly.demo.entity.*;
import com.fly.demo.util.DownloadUtil;
import com.fly.demo.util.MathUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "ThreadTest")
public class ThreadTest {

    public static void testThread1() {
        ThreadDemo t = new ThreadDemo();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                t.sum();
            }
            LockSupport.park();
        });
        t1.start();

        Runnable runnable = () -> log.info("第二个线程");
        Thread t2 = new Thread(runnable);
        t2.setName("线程2");
        t2.start();


        FutureTask futureTask = new FutureTask(() -> {
            log.info("running");
            return 0;
        });

        Thread t3 = new Thread(futureTask, "线程3");
        t3.start();
    }

    public static void testThread2() {
        Room room = new Room();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    room.increment();
                }
            }
        });
        thread.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    room.decement();
                }
            }
        });
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{}", room.getCounter());
    }

    /*
     * 经典买票问题
     */
    public static void sellTicket() {
        TicketWindow ticketWindow = new TicketWindow(10000);

        List<Integer> vector = new Vector<>();
        for (int i = 0; i < 20000; i++) {
            new Thread(() -> {
                int amount = ticketWindow.sell(MathUtil.random(1, 5));
                vector.add(amount);
            }).start();

        }
        int sum = vector.stream().mapToInt(Integer::intValue).sum();


        log.info("剩余票数: " + ticketWindow.getTicketCount());
        log.info("卖出票数: " + sum);
    }

    public static void testThread3() {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            log.info("等待结果...");
            List<String> response = (List<String>)guardedObject.getResponse(2000);
            log.info("结果是: " + response.size());
        }, "消费者").start();

        new Thread(() -> {
            try {
                log.info("正在下载资源...");
                List<String> response = DownloadUtil.download();
                log.info("资源下载完成");
                guardedObject.product(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "生产者").start();
    }

    /*
    * 寄信收信多线程测试
    * */
    public static void testThreadMail() {
        MailBoxes mailBoxes = new MailBoxes();
        for (int i = 0; i < 5; i++) {
            List<People> peoples = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                People people = new People(mailBoxes);
                Thread thread = new Thread(people);
                thread.start();
                peoples.add(people);
            }
            Postman postman = new Postman(mailBoxes, i + 1, peoples);
            Thread thread = new Thread(postman);
            thread.start();
        }
    }

}
