package com.fly.demo.base.leecode;

import java.util.Arrays;

public class LeeTest {

    public static void main(String[] args) {
        int[] countWords = new Solution().circularGameLosers2(5, 2);
        System.out.println(Arrays.toString(countWords));
    }
}
