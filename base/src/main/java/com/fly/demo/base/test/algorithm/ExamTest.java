package com.fly.demo.base.test.algorithm;

public class ExamTest {

    public static void main(String[] args) {
        test2();
    }

    /*
     * 1. 位运算题
     * 一个int数组中, 只有一个整数出现的次数是奇数次, 其他数字出现的次数是偶数次
     * 请找出这个出现奇数次的整数
     */
    public static void test1(){
        int[] testArr = {3, 3, 4, 5, 7, 7, 4, 5, 7};
        //解答:
        //声明一个int整数
        int result = 0;
        // 对这个数组进行遍历
        for (int i = 0; i < testArr.length; i++) {
            //根据异或位运算的特性, 偶数个整数异或相互抵消, 最后只留下奇数次的数
            result ^= testArr[i];
        }
        System.out.println(result);
    }

    /*
     * 2. 位运算题
     * 上一道题的升级版
     * 一个int数组中, 只有两个整数出现的次数是奇数次, 其他数字出现的次数是偶数次
     * 请找出这两个出现奇数次的整数
     */
    public static void test2(){
        int[] testArr = {3, 3, 9, 4, 5, 9, 7, 7, 9, 4, 5, 7, 9, 9};
        //解答:
        //声明一个int整数
        int result = 0;
        // 对这个数组进行第一次遍历
        for (int i = 0; i < testArr.length; i++) {
            //根据异或位运算的特性, 偶数个整数异或相互抵消, 最后只留下奇数次的数
            result ^= testArr[i];
        }
        //假设这两个奇数次的数是 a 和 b, 第一次遍历完后 result = a ^ b
        //并且 a 和 b的值必定不想等, 所以在这两个数的二进制中, 一定有某几位上的值是不相同的, 也就是 分别是 1 和 0
        //接下来找出一个不相同的最低位即可
        // ~ 是取反的意思, 取反加1再与原数 相与, 即可得到最右侧的1
        int n = result & (~result + 1);
        int result2 = 0;
        for (int i = 0; i < testArr.length; i++) {
            if ((testArr[i] & n) > 0){
                result2 ^= testArr[i];
            }
        }
        result = result ^ result2;
        System.out.println(result);
        System.out.println(result2);
    }

    /*
     * 3. 排序题
     * 一个无序的整型数组, 任意相邻的两个数一定不相等, 找出一个局部最小数
     * 局部最小定义: 在 i位置的数, 比 i + 1 位置的数小, 则在 i ~ i+1范围内, i处的数就是局部最小.
     * 同样的, 若i - 1 位置的数大于 i位置的数, 也可保证i位置的数局部最小
     * 但若 i - 1位置的数 小于 i位置的数, 则局部最小就为 i - 1 位置的数
     */

    public static void test3(){
        int[] testArr = {1, 3, 5, 2, 5, 6, 9, 7, 8, 9};
        //使用二分法来解题
        int max = testArr.length;
        int min = 0;
    }

    /*
     * 4. 递归题
     * 一个无序的整数数组, 求给定范围内的最大值
     */
    public static int test4(int[] arr, int l, int r) {
        if (l > r || l > arr.length || r > arr.length){
            return -1;
        }
        if (l == r){
            return arr[l];
        }
        int mid = l + ((r - l) >> 2); //求中点, 注意: 这不是二分思想, 而是分治思想
        int leftMax = test4(arr, l, mid);
        int rightMax = test4(arr, mid + 1, r);
        return Math.max(leftMax, rightMax);
    }
}
