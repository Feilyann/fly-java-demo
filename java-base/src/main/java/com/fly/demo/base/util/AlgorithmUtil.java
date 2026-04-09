package com.fly.demo.base.util;


import java.util.*;

/*
 * 算法工具类
 */
public class AlgorithmUtil {

    public static int cal(int[] arrs, int i, int[] memory) {
        int maxLen = 1;
        for (int j = i + 1; j < arrs.length; j++){
            if (arrs[i] < arrs[j]){
                maxLen = Math.max(maxLen, cal(arrs, j, memory) + 1);
            }
        }
        memory[i] = maxLen;
        return maxLen;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /*
     * 冒泡正序: 由相邻两数依次进行比较, 后面比前面小, 就进行交换
     * [5, 4, 3, 2, 1]
     * 0-1 1-2 2-3 3-4
     * 0-1 1-2 2-3
     * 0-1 1-2
     * 0-1
     */
    public static void bubbleSortAsc(int[] ints) {
        for (int i = 0; i < ints.length - 1; i++) { // 表示总共要循环 (length - 1) 轮
            for (int j = 0; j < ints.length - i - 1; j++) { // 每轮要循环比较的次数
                if (ints[j] > ints[j + 1]) {
                    swap(ints, j, j + 1);
                }
            }
        }
    }

    /*
     * 冒泡倒序
     */
    public static void bubbleSortDesc(int[] ints) {
        for (int i = 0; i < ints.length - 1; i++) { // 表示总共要循环 (length - 1) 轮
            for (int j = 0; j < ints.length - i - 1; j++) { // 每轮要循环比较的次数
                if (ints[j] < ints[j + 1]) {
                    swap(ints, j, j + 1);
                }
            }
        }
    }

    /*
     * 选择正序: 从数列的第一个下标开始, 跟后面的所有元素进行比较, 遇到比自己小的, 就进行交换
     */
    public static void selectSortAsc(int[] ints) {
        for (int i = 0; i < ints.length - 1; i++) { // 表示从下标0开始往后循环
            for (int j = i + 1; j < ints.length; j++) { // 在 i 的下标下, i位置的元素依次与后面的所有元素进行比较
                if (ints[i] > ints[j]) {
                    swap(ints, i, j);
                }
            }
        }
    }

    /*
     * 选择倒序
     */
    public static void selectSortDesc(int[] ints) {
        for (int i = 0; i < ints.length - 1; i++) { // 表示从下标0开始往后循环
            for (int j = i + 1; j < ints.length; j++) { // 在 i 的下标下, i位置的元素依次与后面的所有元素进行比较
                if (ints[i] < ints[j]) {
                    swap(ints, i, j);
                }
            }
        }
    }

    /*
     * 插入正序
     * 注意: 插入排序是寻找正确的插入位置, 而不是两个位置的元素比较相互交换的思想
     * 实例数组: {3, 4, 6, 2, 9, 8, 1, 5, 7}
     * 第一次循环: 保证 0-0 范围有序   此时, i = 0, j = -1. 3本身一定有序
     * 第二次循环: 保证 0-1 范围有序   此时, i = 1, j = 0. 比较的是4与3, 有序
     * 第三次循环: 保证 0-2 范围有序   此时, i = 2, j = 1. 先比较6与4, 有序, 直接开始下一次循环
     * 第四次循环: 保证 0-3 范围有序   此时, i = 3, j = 2. 先比较2与6, 6 > 2, 进行交换. 再重复, 比较2与4, 4 > 2, 交换. 3 > 2, 交换
     * ...
     * 最后一次循环: 保证 0-8 范围有序, 即全部有序
     */
    public static void insertSortAsc(int[] ints) {
        //首先判断数组是否为空或者长度是否小于2
        if (ints == null || ints.length < 2){
            return;
        }
        for (int i = 0; i < ints.length; i++){ //第一层循环是做到 0 ~ i 有序
            for (int j = i - 1; j >= 0 && ints[j] > ints[j + 1]; j--){
                swap(ints, j, j + 1);
            }
        }
    }

    /*
     * 插入倒序
     * 注意: 插入排序是寻找正确的插入位置, 而不是两个位置的元素比较相互交换的思想
     */
    public static void insertSortDesc(int[] ints) {
        //首先判断数组是否为空或者长度是否小于2
        if (ints == null || ints.length < 2){
            return;
        }
        for (int i = 0; i < ints.length; i++){ //第一层循环是做到 0 ~ i 有序
            for (int j = i - 1; j >= 0 && ints[j] < ints[j + 1]; j--){
                swap(ints, j, j + 1);
            }
        }
    }

    /*
     * 归并正序
     */
    public static void mergeSort(int[] arrs) {
        mergeSort(arrs, 0, arrs.length - 1);
    }

    public static void mergeSort(int[] arrs, int left, int right) {
        if (left < right) {
//            int mid = (left + right) >> 1; //不要使用 相加的方式求中点
            int mid = left + (right - left) >> 1; //而要使用左边加上 (右边 - 左边)的方式, 这样是为了避免当left值过大的时候, (left + right) / 2 会出现异常错误结果
            mergeSort(arrs, left, mid); //递归先整理左边区域
            mergeSort(arrs, mid + 1, right);
            sort(arrs, left, mid, right); //开始对left~right范围排序
        }
    }

    /**
     *
     * @param array 待排序的数组
     * @param left  左边有序子数组的初始索引
     * @param mid   中间索引
     * @param right  右边的索引
     */
    private static void sort(int[] array,int left,int mid,int right){
        int[] tempArray = new int[right - left + 1];
        int i = left;//初始化i，左边有序子数组的初始索引
        int j = mid+1;//初始化j，右边有序子数组的初始索引
        int index = 0;//用于标识tempArray数组当前所在的下标
        //(一)
        //先把左右两边(有序)的子数组按照规则填充到tempArray数组
        //直到左右两边的有序子数组，有一边处理完毕为止
        while (i <= mid && j <= right){ //当i小于中点 并且 j小于右边点时, 说明此时的左右区域数组还没填充完毕
            tempArray[index++] = array[i] <= array[j] ? array[i++] : array[j++];
        }
        //(二)
        //把有剩余数据的一边的数据依次全部填充到temp
        while (i <= mid){//说明左边的子数组有剩余元素
            tempArray[index++] = array[i++];
        }
        while (j <= right){//说明右边的子数组有剩余元素
            tempArray[index++] = array[j++];
        }
        //(三)
        //将tempArray数组的元素拷贝到array
        index=0;
        int tempLeft = left;
        while (tempLeft <= right){
            array[tempLeft++] = tempArray[index++];
        }
    }


    /*
     * 快排正序
     *
     */
    public static void fastSortAsc(int[] arrs) {
        fastSortAsc(arrs, 0, arrs.length - 1);
    }

    public static void fastSortAsc(int[] arrs, int low, int high) {
        if (low < high){
            int position = partition(arrs, low, high);
            //左子数组递归
            fastSortAsc(arrs, low, position - 1);
            //右子树组递归
            fastSortAsc(arrs, position + 1, high);
        }
    }

    //分组方法
    private static int partition(int[] arrs, int low, int high) {
        //取最后一个元素作为中心元素
        int center =  arrs[high];
        //定义指向比中心元素大的指针, 指针从中心元素开始
        int pointer = low;
        //遍历数组中的所有元素, 将比中心元素大的放在右边, 比中心元素小的放在左边
        for (int i = low; i < high; i++) {
            if (arrs[i] < center){
                //将比中心元素小的元素与指针交换位置
                //如果第一个元素比中心元素小, 那么就是第一个元素自己与自己交换位置, 指针指向下一个元素
                //如果第一个元素比中心元素大, 索引向下移动, 指针指向这个较大的元素, 直到找到比中心元素小的元素, 交换指针, 指针向下移动
                int temp = arrs[i];
                arrs[i] = arrs[pointer];
                arrs[pointer] = temp;
                pointer++;
            }
        }
        //将中心元素和指针指向的元素交换位置
        int temp = arrs[pointer];
        arrs[pointer] = arrs[high];
        arrs[high] = temp;
        return pointer;
    }

    /*
     * 快排倒序
     */
    public static void fastSortDesc(int[] ints, int low, int high) {
        //这四个变量分别为哨兵i, 哨兵j, 临时交换, 基准位的值
        if (low >= high) {
            return;
        }
        int i, j, temp, base;
        i = low;
        j = high;
        base = ints[low];
        while (i < j) {
            while (base >= ints[j] && i < j){
                j--;
            }
            while (base <= ints[i] && i < j) {
                i++;
            }
            if (i < j){
                temp = ints[j];
                ints[j] = ints[i];
                ints[i] = temp;
            }
        }
        ints[low] = ints[i];
        ints[i] = base;
        fastSortAsc(ints, low, j - 1);
        fastSortAsc(ints, j + 1,  high);
    }

    /*
     * 二分查找 (非递归)
     * 注意: 要求数组必须是有序的
     */
    public static int binarySearch(int[] arrs, int target) {
        int mid;
        int l = 0, r = arrs.length - 1;
        while (l <= r) {
            mid = l + ((r - l) >> 1);
            if (arrs[mid] == target) {
                return mid;
            } else if (arrs[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    /*
     * 二分查找 (递归实现)
     * 注意: 要求数组必须是有序的
     */
    public static int binaryRecurSearch(int[] arrs, int target, int l, int r) {
        if (l > r) {
            return -1;
        }
        int mid = l + ((r - l) >> 1);
        if (arrs[mid] == target) {
            return mid;
        } else if (arrs[mid] > target) {
            return binaryRecurSearch(arrs, target, l, mid - 1);
        } else {
            return binaryRecurSearch(arrs, target, mid + 1, r);
        }
    }

    /*
     * ----------------------------BF算法 Start----------------------------
     * 算法思想:
     */

    public static int bfSearch(String s, String p) {
        int sL = s.length(), pL = p.length();
        for (int i = 0; i < sL - pL; i++) {
            int j = 0;
            while (j < pL) {
                if (s.charAt(i + j) != p.charAt(j)) {
                    break;
                }
                j++;
            }
            if (j == pL) {
                return i;
            }
        }
        return -1;
    }

    /*
     * ----------------------------BF算法 End----------------------------
     */

    /*
     * ----------------------------KMP算法 Start----------------------------
     */

    //计算next数组
    //参数: p, 指的是模式串pattern, 也就是待匹配的字符串
    private static int[] getNext(String[] pArrs) {
        int[] nextArrs = new int[pArrs.length];
        nextArrs[0] = -1;
        int k = -1, j = 0;
        while (j < pArrs.length - 1) {
            if (k == -1 || Objects.equals(pArrs[k], pArrs[j])) {
                ++k;
                ++j;
                if (!Objects.equals(pArrs[j], pArrs[k])) {
                    nextArrs[j] = k;
                } else {
                    nextArrs[j] = nextArrs[k];
                }
            } else {
                k = nextArrs[k];
            }
        }
        return nextArrs;
    }

    public static int kmpSearch(String s, String p) {
        String[] sArrs = s.split("");
        String[] pArrs = p.split("");
        int[] next = getNext(pArrs);
        int i = 0, j = 0;
        while (i < sArrs.length && j < pArrs.length) {
            if (j == -1 || Objects.equals(sArrs[i], pArrs[j])) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (pArrs.length == j) {
            return i - j;
        }
        return -1;
    }

    /*
     * ----------------------------KMP算法 End----------------------------
     */

    /*
     * ----------------------------BM算法 Start----------------------------
     */
    // BM算法匹配字符串，匹配成功返回P在S中的首字符下标，匹配失败返回-1
    public static int bmSearch(String source, String pattern) {
        char[] src = source.toCharArray();
        char[] ptn = pattern.toCharArray();
        int sLen = src.length;
        int pLen = ptn.length;

        // 模式串为空字符串，返回0
        if (pLen == 0) {
            return 0;
        }
        // 主串长度小于模式串长度，返回-1
        if (sLen < pLen) {
            return -1;
        }

        int[] BC = buildBadCharacter(ptn);
        int[] GS = buildGoodSuffix(ptn);

        // 从尾部开始匹配，其中i指向主串，j指向模式串
        for (int i = pLen - 1; i < sLen; ) {
            int j = pLen - 1;
            for (; src[i] == ptn[j]; i--, j--) {
                if (j == 0) {   // 匹配成功返回首字符下标
                    return i;
                }
            }

            // 每次后移“坏字符规则”和“好后缀规则”两者的较大值
            // 注意此时i（坏字符）已经向前移动，所以并非真正意义上的规则
            i += Math.max(BC[src[i]], GS[pLen - 1 - j]);
        }

        return -1;
    }

    // 坏字符规则表
    private static int[] buildBadCharacter(char[] pattern) {
        int pLen = pattern.length;
        final int CHARACTER_SIZE = 256; // 英文字符的种类，2^8
        int[] BC = new int[CHARACTER_SIZE]; // 记录坏字符出现时后移位数

        Arrays.fill(BC, pLen);  // 默认后移整个模式串长度
        for (int i = 0; i < pLen - 1; i++) {
            int ascii = pattern[i];  // 当前字符对应的ASCII值
            BC[ascii] = pLen - 1 - i;   // 对应的后移位数，若重复则以最右边为准
        }

        return BC;
    }

    // 非真正意义上的好字符规则表，后移位数还加上了当前好后缀的最大长度
    private static int[] buildGoodSuffix(char[] pattern) {
        int pLen = pattern.length;
        int[] GS = new int[pLen];   // 记录好后缀出现时后移位数
        int lastPrefixPos = pLen;   // 好后缀的首字符位置

        for (int i = pLen - 1; i >= 0; i--) {
            // 判断当前位置（不含）之后是否是好后缀，空字符也是好后缀
            if (isPrefix(pattern, i + 1)) {
                lastPrefixPos = i + 1;
            }
            // 如果是好后缀，则GS=pLen，否则依次为pLen+1、pLen+2、...
            GS[pLen - 1 - i] = lastPrefixPos - i + pLen - 1;
        }

        // 上面在比较好后缀时，是从模式串的首字符开始的，但实际上好后缀可能出现在模式串中间。
        // 比如模式串EXAMPXA，假设主串指针在比较P时发现是坏字符，那么XA就是好后缀，
        // 虽然它的首字符X与模式串的首字符E并不相等。此时suffixLen=2表示将主串指针后移至模式串末尾，
        // pLen-1-i=4表示真正的好字符规则，同样主串指针后移，使得模式串前面的XA对齐主串的XA
        for (int i = 0; i < pLen - 1; i++) {
            int suffixLen = suffixLength(pattern, i);
            GS[suffixLen] = pLen - 1 - i + suffixLen;
        }

        return GS;
    }

    // 判断是否是好后缀，即模式串begin（含）之后的子串是否匹配模式串的前缀
    private static boolean isPrefix(char[] pattern, int begin) {
        for (int i = begin, j = 0; i < pattern.length; i++, j++) {
            if (pattern[i] != pattern[j]) {
                return false;
            }
        }

        return true;
    }

    // 返回模式串中以pattern[begin]（含）结尾的后缀子串的最大长度
    private static int suffixLength(char[] pattern, int begin) {
        int suffixLen = 0;

        int i = begin;
        int j = pattern.length - 1;
        while (i >= 0 && pattern[i] == pattern[j]) {
            suffixLen++;
            i--;
            j--;
        }

        return suffixLen;
    }

    /*
     * ----------------------------BM算法 End----------------------------
     */

    /*
     * ----------------------------Sunday算法 Start----------------------------
     */

    public static int sundaySearch(String haystack, String needle) {
        int hayLen = haystack.length();
        int nLen = needle.length();

        int i = 0;//haystack串的游标索引
        int j = 0;// needle串的游标索引

        // haystack剩余字符少于needle串时跳过比较
        while (i <= hayLen - nLen) {
            // 将needle串与haystack串中参与比较的子串进行逐个字符比对
            while (j < nLen && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }

            // 如果j等于needle串的长度说明此时匹配成功，可以直接返回此时主串的游标索引
            if (j == nLen) {
                return i;
            }

            // 不匹配时计算需要跳过的字符数，移动主串游标i
            if (i < hayLen - nLen) {
                // 对照字符在needle串存在，则需要跳过的字符数为从对照字符在needle串中最后出现的位置起剩余的字符个数
                // 不存在则跳过的字符数为needle串长度+1，也就是代码nLen-(-1)的情况
                i += (nLen - lastIndex(needle, haystack.charAt(i + nLen)));
            } else {
                return -1;
            }
            // 每次比较之后将needle游标置为0
            j=0;
        }

        return -1;
    }

    private static int lastIndex(String str, char ch) {
        // 从后往前检索
        for (int j = str.length() - 1; j >= 0; j--) {
            if (str.charAt(j) == ch) {
                return j;
            }
        }
        return -1;
    }

    /*
     * ----------------------------Sunday算法 End----------------------------
     */

    /*
     * ----------------------------根据Map的value值排序 Start----------------------------
     */

    public static void sortMapAsc(Map<Object, Integer> map) {
        List<Map.Entry<Object, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));
        Iterator<Map.Entry<Object, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Integer> next = iterator.next();
            System.out.println(next.getKey() + " : " + next.getValue());
        }
    }

    public static void sortMapDesc(Map<Object, Integer> map) {
        List<Map.Entry<Object, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Iterator<Map.Entry<Object, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Integer> next = iterator.next();
            System.out.println(next.getKey() + " : " + next.getValue());
        }
    }

    /*
     * ----------------------------根据Map的value值排序 End----------------------------
     */
}
