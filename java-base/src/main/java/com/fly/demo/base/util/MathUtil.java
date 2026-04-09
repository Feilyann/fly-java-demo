package com.fly.demo.base.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtil {

    public static int random() {
        return new Random().nextInt();
    }

    public static int random(int bound) {
        return new Random().nextInt(bound);
    }

    public static int random(int start, int end) {
        if (start < end) {
            return new Random().nextInt(end - start + 1) + start;
        } else if (start > end) {
            return new Random().nextInt(start - end + 1) + end;
        }
        return 0;
    }

    //16进制转10进制
    public static int hexadecimalToDecimal(String s) {
        int result = 0;

        for(int i = 0; i < s.length() - 2; i++){
            char c = s.charAt(i + 2);
            int num = 0;
            if(c >= '0' && c <= '9'){
                num = c - '0';
            } else if(c >= 'A' && c <= 'F') {
                num = c - 'A' + 10;
            } else if(c >= 'a' && c <= 'f') {
                num = c - 'a' + 10;
            }
            result += num * Math.pow(16, s.length() - i - 3);
        }
        return result;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
