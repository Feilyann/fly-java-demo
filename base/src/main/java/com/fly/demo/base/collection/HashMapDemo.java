package com.fly.demo.base.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapDemo {

    public static void main(String[] args) {
        testHashMap();
    }

    public static void testSet() {
        String s = new String();
        Set set = new HashSet();
    }

    public static void testHashMap() {
        Map<String, Object> map = new HashMap();

        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);

//        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Object> next = iterator.next();
//            map.remove(next.getKey());
//            iterator.remove();
//        }
    }

    public static void testHashTable() {
        Hashtable hashtable = new Hashtable();
        hashtable.put(1, 1);
        hashtable.get(1);
        hashtable.remove(1);
    }

    public static void testConcurrentHashMap() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    }
}
