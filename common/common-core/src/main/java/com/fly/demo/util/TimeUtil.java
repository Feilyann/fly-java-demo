package com.fly.demo.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public final static DateTimeFormatter FORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public final static ZoneOffset BEIJING = ZoneOffset.ofHours(8);

    public static long getMillisecond() {
        return Instant.now().toEpochMilli();
    }

    public static String getCurrTime() {
        return LocalDateTime.now().format(FORMATTER1);
    }

    public static String milliSecondsToStr(long milliSeconds) {
        return milliSecondsToStr(FORMATTER1, milliSeconds);
    }

    public static String milliSecondsToStr(String pattern, long milliSeconds) {
        return milliSecondsToStr(DateTimeFormatter.ofPattern(pattern), milliSeconds);
    }

    public static String milliSecondsToStr(DateTimeFormatter patternFormatter, long milliSeconds) {
        if (String.valueOf(milliSeconds).length() == 10) {
            milliSeconds *= 1000;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliSeconds), BEIJING).format(patternFormatter);
    }

    public static String secondsToStr(long seconds) {
        return milliSecondsToStr(FORMATTER1, seconds);
    }

    public static String secondsToStr(String pattern, long seconds) {
        return secondsToStr(DateTimeFormatter.ofPattern(pattern), seconds);
    }

    public static String secondsToStr(DateTimeFormatter patternFormatter, long seconds) {
        if (String.valueOf(seconds).length() == 13) {
            seconds /= 1000;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), BEIJING).format(patternFormatter);
    }

    public static long format(String time, String pattern) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern))
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
