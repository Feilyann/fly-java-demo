package com.fly.demo;

import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isBlack(String str) {
        return str == null || str.length() == 0;
    }

    public static String getString(String separator, Object... strs) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            buffer.append(strs[i]);
            if (i < strs.length - 1) {
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }

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

    public static String camelToUnderline(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]");
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == 0) {
                c = Character.toLowerCase(c);
            } else if (pattern.matcher(String.valueOf(c)).matches()){
                buffer.append("_");
                c = Character.toLowerCase(c);
            }
            buffer.append(c);
        }
        return buffer.toString();
    }
}
