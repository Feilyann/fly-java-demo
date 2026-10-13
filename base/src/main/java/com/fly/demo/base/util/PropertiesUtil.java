package com.fly.demo.base.util;

import java.util.HashMap;
import java.util.Map;

public class PropertiesUtil {

    private final static Map<String, String> properties = new HashMap<>();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    }
}
