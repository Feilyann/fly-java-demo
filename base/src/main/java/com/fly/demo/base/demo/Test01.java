package com.fly.demo.base.demo;

import java.util.Calendar;

public class Test01 {

    static {
        System.out.println("static block test");
    }

    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
    }


    public static void testExchange() {
        int a = 3;
        int b = 4;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a: " + a + "\nb: " + b);
    }

    public static void testOperate() {
        int x = 5;
        System.out.println(x & (1 << 1));
    }
}
