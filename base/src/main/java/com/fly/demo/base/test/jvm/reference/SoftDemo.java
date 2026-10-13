package com.fly.demo.base.test.jvm.reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/*
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class SoftDemo {

    public static void main(String[] args) {
        //软引用
        List<SoftReference<byte[]>> softReferenceList = new ArrayList<SoftReference<byte[]>>();

        //引用队列
        ReferenceQueue<byte[]> queue = new ReferenceQueue();
        for (int i = 1; i <= 5; i++) {
            SoftReference<byte[]> bytes = new SoftReference(new byte[1024 * 1024 * 4], queue);
            softReferenceList.add(bytes);
            System.out.print("第" + i + "次:");
            System.out.println(bytes);
        }
        System.out.println("软引用添加循环结束");

        //?????
        Reference<? extends byte[]> poll = queue.poll();
        while (poll != null) {
            softReferenceList.remove(poll);
            poll = queue.poll();
        }

        int count = 1;
        for (SoftReference<byte[]> softReference : softReferenceList) {
            System.out.print("第" + count + "次:");
            System.out.println(softReference.get());
            count++;
        }
    }
}
