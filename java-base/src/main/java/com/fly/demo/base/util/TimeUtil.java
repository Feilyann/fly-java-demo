package com.fly.demo.base.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static long getMillisecond() {
        Instant instant = Instant.now();
        return instant.toEpochMilli();
    }

    public static String getCurrTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.now();
        return localDate.format(formatter);
    }

    public static LocalDateTime strToTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, formatter);
    }

    public static LocalDateTime millToTime(long time, String zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of(zone));
    }
}
