package com.fly.demo.util;

public class BooleanUtil {

    public static boolean is(int flag) {
        return flag == 1;
    }

    public static int parse(boolean flag) {
        return flag ? 1 : 0;
    }
}
