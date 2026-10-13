package com.fly.demo.base.util;

public class StringUtil {

    public static boolean isDigit(String input) {
        if (input == null || input.length() == 0)
            return false;
        input = input.trim();
        if ("".equals(input))
            return false;
        if (input.startsWith("-")) {
            return input.substring(1).matches("[0-9]*");
        } else {
            return input.matches("[0-9]*");
        }
    }
}
