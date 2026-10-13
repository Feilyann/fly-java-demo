package com.fly.demo.base.test.lock;

import java.util.concurrent.*;

public class TreadPoolTest {

    public void test() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });
    }
}
