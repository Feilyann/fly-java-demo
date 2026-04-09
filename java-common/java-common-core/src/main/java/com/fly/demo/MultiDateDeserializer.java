package com.fly.demo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final Map<String, DateTimeFormatter> FORMATTER_MAP;

    static {
        FORMATTER_MAP = new HashMap<>();
        FORMATTER_MAP.put("[1-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        FORMATTER_MAP.put("[1-9][0-9][0-9][0-9]/[0-1][0-9]/[0-3][0-9]", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
    
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentToken() == JsonToken.VALUE_STRING) {
            String dateStr = p.getText().trim();
            if (dateStr.isEmpty()) {
                return null;
            }
            dateStr = check(dateStr);
            // 遍历所有格式器，尝试解析
            for (String regex : FORMATTER_MAP.keySet()) {
                Matcher matcher = Pattern.compile(regex).matcher(dateStr);
                if (matcher.matches()) {
                    return LocalDate.parse(dateStr, FORMATTER_MAP.get(regex));
                }
            }
            // 所有格式都失败了，抛出有意义的异常
            throw ctxt.weirdStringException(dateStr, LocalDate.class, 
                    "日期字符串不符合任何支持的格式: " + FORMATTER_MAP.keySet());
        }
        // 如果不是字符串值，让 Jackson 按默认方式处理（或抛出异常）
        return (LocalDate) ctxt.handleUnexpectedToken(LocalDate.class, p);
    }

    private String check(String dateStr) {
        if (Pattern.compile("[1-9][0-9][0-9][0-9]").matcher(dateStr).matches()) {
            dateStr = dateStr + "-01-01";
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]-[0-1][0-9]").matcher(dateStr).matches()) {
            dateStr = dateStr + "-01";
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]-[0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("-");
            dateStr = strings[0] + "-0" + strings[1] + "-01";
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]-[0-9]-[0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("-");
            dateStr = strings[0] + "-0" + strings[1] + "-0" + strings[2];
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]-[0-1][0-9]-[0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("-");
            dateStr = strings[0] + "-" + strings[1] + "-0" + strings[2];
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]-[0-9]-[0-3][0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("-");
            dateStr = strings[0] + "-0" + strings[1] + "-" + strings[2];
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]/[0-1][0-9]").matcher(dateStr).matches()) {
            dateStr = dateStr + "/01";
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]/[0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("/");
            dateStr = strings[0] + "/0" + strings[1] + "/01";
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]/[0-9]/[0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("/");
            dateStr = strings[0] + "/0" + strings[1] + "/0" + strings[2];
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]/[0-1][0-9]/[0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("/");
            dateStr = strings[0] + "/" + strings[1] + "/0" + strings[2];
        } else if (Pattern.compile("[1-9][0-9][0-9][0-9]/[0-9]/[0-3][0-9]").matcher(dateStr).matches()) {
            String[] strings = dateStr.split("/");
            dateStr = strings[0] + "/0" + strings[1] + "/" + strings[2];
        }
        return dateStr;
    }
}