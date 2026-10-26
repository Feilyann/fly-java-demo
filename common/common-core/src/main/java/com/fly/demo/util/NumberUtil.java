package com.fly.demo.util;

public class NumberUtil {

    public static Double getDoubleValue(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof String) {
            try {
                return Double.parseDouble(((String) obj).trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static Double getDoubleValue(String str) {
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
