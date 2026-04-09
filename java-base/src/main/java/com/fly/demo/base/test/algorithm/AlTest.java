package com.fly.demo.base.test.algorithm;

import com.fly.demo.entity.EMail;

import java.util.Arrays;

public class AlTest {

    public static void main(String[] args) {
        String s = "IDIDDII";
        diStringMatch(s);
    }

    /* 贪心算法:
     * 由范围 [0,n] 内所有整数组成的 n + 1 个整数的排列序列可以表示为长度为 n 的字符串 s ，其中:
    如果 perm[i] < perm[i + 1] ，那么 s[i] == 'I' 
    如果 perm[i] > perm[i + 1] ，那么 s[i] == 'D' 
    给定一个字符串 s ，重构排列 perm 并返回它。如果有多个有效排列perm，则返回其中 任何一个 。
     */
    public static void diStringMatch(String s) {
        int n = s.length(), lw = 0, hi = n;
        int[] re = new int[n + 1];
        for (int i = 0; i < n; i++) {
            re[i] = 'I' == s.charAt(i) ? lw++ : hi--;
        }
        re[n] = lw;
        System.out.println(Arrays.toString(re));
    }

}
