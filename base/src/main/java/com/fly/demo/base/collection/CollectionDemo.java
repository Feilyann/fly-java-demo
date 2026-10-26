package com.fly.demo.base.collection;

import java.util.*;

public class CollectionDemo {

    public static void main(String[] args) {
        testArrayList();
//        testLinkedList();
    }

    public static void testArrayList() {
        List arrayList = new ArrayList();

        for (int i = 1; i <= 10; i++) {
            arrayList.add(i);
        }

        arrayList.add(3, 4);
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (Integer.valueOf(o.toString()) == 5) {
                iterator.remove();
            }
        }
        System.out.println(Arrays.toString(arrayList.toArray()));
    }

    public static void testVector() {
        Vector vector = new Vector();
        vector.add(1);
    }

    public static void testLinkedList() {
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        Iterator iterator = linkedList.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            linkedList.add(4);
        }

        Collections.synchronizedCollection(linkedList);
        System.out.println(Arrays.toString(linkedList.toArray()));
    }

}
