package com.fly.demo.base.entity;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/*
* 个人类
* */
@Data
public class Person extends People {

    private int index;

    private Money money;

    private static Unsafe UNSAFE;

    private static long offset;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public class Man {
        private String name;

        private Integer sex;

        public String getName;

        public Man(String name) {
            this.name = name;
        }
    }



    public Person() {
    }

    public Person(int index, Money money) {
        this.index = index;
        this.money = money;
    }

    public Person(Money money) {
        this.money = money;
    }

    public void run() {
        try {
            offset = UNSAFE.objectFieldOffset(money.getClass().getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                boolean b = UNSAFE.compareAndSwapInt(money, offset, money.getValue(), money.getValue() + 1);
                if (b) {
                    System.out.println(UNSAFE.getIntVolatile(money, offset));
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                boolean b = UNSAFE.compareAndSwapInt(money, offset, money.getValue(), money.getValue() + 1);
                if (b) {
                    System.out.println(UNSAFE.getIntVolatile(money, offset));
                }
            }
        }).start();
    }

    @Override
    public String toString() {
        return "Person{" +
                "index=" + index +
                ", money=" + money +
                '}';
    }
}
