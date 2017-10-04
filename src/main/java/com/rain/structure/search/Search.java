package com.rain.structure.search;

public class Search {
    /**
     * 无需判断数组是否越界,哨兵减少了代码的编写量
     *  传统的for循环需要写一定的代码
     * */
    public static <T> boolean sequenceSearch(T[] t, T target) {
        if (t == null) {
            return false;
        }
        t[0] = target;
        int i;
        for (i = t.length - 1;t[i] != target;i --);
        return i == 0 ? false : true;
    }
    
    /**
     * 折半查找
     * */
    public static boolean binarySearch(Integer[] t, Integer target) {
        if (t == null) {
            return false;
        }
        int low = 0;
        int high = t.length - 1;
        int mid = (low + high) / 2;
        while (low <= high) {
            if (t[mid].compareTo(target) == 0) {
                return true;
            }
            if (t[mid].compareTo(target) > 0) {
                high = mid - 1;
            }
            if (t[mid].compareTo(target) < 0) {
                low = mid + 1;
            }
            mid = (low + high) / 2;
        }
        return false;
    }
    
    /**
     * 获取两个有序序列中存在的相同元素
     * @param a
     * @param b
     */
    public static void getCommonSequenceOf2Array(Integer[] a, Integer[] b) {
        int one = 0;
        int two = 0;
        int k = 0;
        Integer[] copy = new Integer[a.length > b.length ? a.length : b.length];
        while (one < a.length && two < b.length) {
            if (a[one] < b[two]) {
                one ++;
            } else if (a[one] == b[two]) {
                copy[k ++] = a[one];
                one ++;
                two ++;
            } else {
                two ++;
            }
        }
    }
    
    /**
     * 获取数组中所有元素组合的最大元素(未考虑负数)
     * @param a
     */
    public static void getMaxDigitInArray(Integer[] a) {
        for (int i = 0;i < a.length - 1; i ++) {
            for (int j = 0;j < a.length - 1 - i;j ++) {
                if (compare(a[j], a[j ++])) {
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
    
    /**
     * 比较两个整数平起来的数字,逆序大还是正序大
     * @param a
     * @param b
     * @return
     */
    public static boolean compare(int a, int b) {
        String s1 = a + "" + b;
        String s2 = b + "" + a;
        if (s1.compareTo(s2) < 0) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        System.out.println(binarySearch(new Integer[]{1, 2, 3, 4, 5, 6, 6, 7, 10}, 11));
    }
}
