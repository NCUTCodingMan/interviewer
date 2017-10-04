package com.rain.structure.loop;

/**
 * 关于递归与分治的几个问题
 * 1.求a的幂
 * 2.求n个元素中最小的第k个元素
 * 3.快速排序
 * 4.归并排序
 * 5.青蛙跳台阶
 * 6.二叉树的高度
 * */
public class Loop {
    // 采用分治思想计算a^n
    public static long caculator(int a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            long left = caculator(a, low, mid);
            long right = caculator(a, mid + 1, high);
            return left * right;
        } else {
            return a;
        }
    }
    
    // 求n个元素中最小的第k个元素,也是基于分治;利用快速排序的思想
    public static int partiton(int[] array, int low, int high) {
        int pivot = array[low];
        while (low < high) {
            while (low < high && array[high] > pivot ) {
                high --;
            }
            array[low] = array[high];
            while (low < high && array[low] < pivot) {
                low ++;
            }
            array[high] = array[low];
        }
        array[low] = pivot;
        return low;
    }
    
    public static int selectKthMinus(int[] array, int low, int high, int k) {
        int seq = k - 1;
        if (low <= high) {
            int position = partiton(array, low, high);
            int left = position;
            if (left == seq) {
                return array[position];
            } else if (left < seq) {
                return selectKthMinus(array, position + 1, high, k);
            } else if (left > seq) {
                return selectKthMinus(array, low, position - 1, k);
            }
        }
        return -1;
    }
    
    /**
     * 采用穷举的方式可以求出无序数组中差值最大差值
     *  此处使用的思路类似冒泡排序,当然可以使用快速排序,插入排序
     *  对排好序的数组求最大差值,时间复杂度相当高o(n^2)
     * @param array
     * @param low
     * @param high
     * @return 差值
     */
    public static int distance(int[] array, int low, int high) {
        int distance = 0;
        for (int i = 0;i < array.length - 1;i ++) {
            for (int j = i + 1;j < array.length;j ++) {
                if (Math.abs(array[i] - array[j]) > distance) {
                    distance = Math.abs(array[i] - array[j]);
                }
            }
        }
        return distance;
    }
    
    public static int distance(int array[], int low, int high, boolean loop) {       
        return 1;
    }
    
    /**
     * 青蛙跳台阶,下面的算法与斐波那契数列计算方法类似
     * 但是存在一个问题,即重复计算;算法时间复杂度相当高
     * @param n
     * @return
     */
    public static int steps(int n) {
        if (n < 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            return steps(n - 1) + steps(n - 2);
        }
    }
    
    /**
     * 从上到下计算最终的结果,算法时间复杂度为o(n)
     * @param n
     * @param flag
     * @return
     */
    public static int steps(int n, boolean flag) {
        if (n < 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        
        int one = 1; // 一个台阶的走法
        int two = 2; // 两个台阶的走法
        int count = 0;
        for (int i = 2;i < n;i ++) {
            count = one + two;
            one = two;
            two = count;
        }
        
        return count;
    }
    
    /**
     * 变态n阶问题,每次可以跳1, 2, 3, ..., n个台阶,共有多少种方式(一共n个台阶)
     * @param n
     * @param flag
     * @param difficult
     * @return
     */
    public static int steps(int n, boolean flag, boolean difficult) {
        int count = 0;
        if (n < 0) {
            return -1;
        }
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            for(int k = 1;k <= n - 1;k ++)
                count+=steps(n - k, false, true);
            count ++;
        }
        
        return count;
    }
    
    public static void main(String[] args) {
        System.out.println(steps(3));
        System.out.println(steps(3, false));
        System.out.println(steps(3, false, true));
    }
}