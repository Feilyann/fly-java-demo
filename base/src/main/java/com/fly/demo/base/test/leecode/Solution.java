package com.fly.demo.base.test.leecode;

import java.util.*;

public class Solution {

    /*
     * 给你两个字符串数组 words1 和 words2 ，请你返回在两个字符串数组中 都恰好出现一次 的字符串的数目。
     */
    public int countWords(String[] words1, String[] words2) {
        List<String> countList = new ArrayList<>(Math.max(words1.length, words2.length));
        Set<String> words = new HashSet<>();
        for(int i = 0; i < words1.length; i++) {
            int count = 0;
            String word = words1[i];
            for(int j = 0; j < words2.length; j++) {
                if(word.equals(words2[j])) {
                    count ++;
                }
            }
            if(words.add(word) && count == 1) {
                countList.add(word);
            } else {
                countList.remove(word);
            }
        }
        return countList.size();
    }

    /*
     * n 个朋友在玩游戏。这些朋友坐成一个圈，按 顺时针方向 从 1 到 n 编号。准确的说，从第 i 个朋友的位置开始顺时针移动 1 步会到达第 (i + 1) 个朋友的位置（1 <= i < n），而从第 n 个朋友的位置开始顺时针移动 1 步会回到第 1 个朋友的位置。
     * 游戏规则如下：
     *
     * 第 1 个朋友接球。
     *
     * 接着，第 1 个朋友将球传给距离他顺时针方向 k 步的朋友。
     * 然后，接球的朋友应该把球传给距离他顺时针方向 2 * k 步的朋友。
     * 接着，接球的朋友应该把球传给距离他顺时针方向 3 * k 步的朋友，以此类推。
     * 换句话说，在第 i 轮中持有球的那位朋友需要将球传递给距离他顺时针方向 i * k 步的朋友。
     *
     * 当某个朋友第 2 次接到球时，游戏结束。
     *
     * 在整场游戏中没有接到过球的朋友是 输家 。
     *
     * 给你参与游戏的朋友数量 n 和一个整数 k ，请按升序排列返回包含所有输家编号的数组 answer 作为答案。
     */
    public int[] circularGameLosers(int n, int k) {
        Set<Integer> winnerSet = new HashSet<>(n);
        int roundCount = 1;
        int ballNum = 1;
        winnerSet.add(ballNum);
        while (true) {
            ballNum = (ballNum + roundCount * k) % n;
            if (!winnerSet.add(ballNum)) {
                break;
            }
            roundCount++;
        }
        List<Integer> losers = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            if(!winnerSet.contains(i)) {
                losers.add(i);
            }
        }
        return losers.stream().mapToInt(Integer::valueOf).toArray();
    }
    public int[] circularGameLosers2(int n, int k) {
        int[] arr = new int[n];
        int i = 0; // 当前球位置
        int count = 0; // 第几轮
        while (arr[i] != 2) {
            i = (i + count * k) % n;
            count++;
            arr[i]++;
        }
        int[] res = new int[n-count + 1 ];
        for (int j = 0, z = 0; j < n; j++) {
            if (arr[j] == 0) {
                res[z++] = j+1;
            }
        }
        return res;
    }

    /*
     * 给你两个数字字符串 num1 和 num2 ，以及两个整数 max_sum 和 min_sum 。如果一个整数 x 满足以下条件，我们称它是一个好整数：
     * num1 <= x <= num2
     * min_sum <= digit_sum(x) <= max_sum.
     * 请你返回好整数的数目。答案可能很大，请返回答案对 10^9 + 7 取余后的结果。
     * 注意，digit_sum(x) 表示 x 各位数字之和。
     * 提示：
     * 1 <= num1 <= num2 <= 1022
     * 1 <= min_sum <= max_sum <= 400
     */
    public void count(String num1, String num2, int maxSum, int minSum) {
        Long aLong = Long.valueOf(num1.trim());
    }
}