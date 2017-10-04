package com.rain.structure.sort;

import java.util.Arrays;

public class Sort {
    /**
     * 插入排序的思想,即是将一个数字插入到前面已经排好序的队列中.
     * 直到所有的关键字都完成这一过程
     * 插入排序之shell排序将一个序列划分为多个子序列,子序列采用直接插入排序
     *  然后继续修改步长,直到将所有的关键字都放入一个组中,再进行插入排序
     * 直接插入排序适用于基本有序的排序表和数据量不大的排序表
     * */
    public static Integer[] insertSort(Integer[] params) {
        if (params == null) {
            return params;
        }
        int i = 1;
        int j = 0;
        for (i = 1;i < params.length;i ++) {
            // 每一次都从前往后比较,直到直到插入的位置
            for (j = 0;j < i;j ++) {
                if (params[i] > params[j]) {
                    continue;
                } else {
                    // 移动关键字
                    int temp = i - 1;
                    int key = params[i];
                    while (temp >= 0 && temp >= j) {
                        params[temp + 1] = params[temp];
                        temp --;
                    }
                    params[j] = key;
                    break;
                }               
            }  
        }
        return params;
    }
    
    public static Integer[] insertSort(Integer[] params, Integer target) {
        if (params == null) {
            return params;
        }
        int i = 1;
        int j = 0;
        for (i = 1;i < params.length;i ++) {
            // 从后往前比较,而不用每一次都是从前往后比较(与上面写法的差异性)
            if (params[i] < params[i - 1]) {
                int temp = params[i];
                // 注意数组越界的情况
                for (j = i - 1;j >= 0 && params[j] > temp;j --) {
                    params[j + 1] = params[j];
                }
                params[j + 1] = temp;
            }
        }
        return params;
    }
    
    // 折半插入排序,针对已经排好序的序列而言,可以采用折半查找减少比较的次数,找到元素的位置
    public static Integer[] binaryInsertSort(Integer[] params) {
        if (params == null) {
            return params;
        }
        int i = 1;
        int j = 0;
        for (i = 1;i < params.length;i ++) {
            int low = 0;
            int high = i - 1;
            // 折半查找关键字的位置,然后移动元素
            while (low <= high) {
                int mid = (low + high) / 2;
                if (params[mid] > params[i]){
                    high = mid - 1;
                }else {
                    low = mid + 1;
                }
            }
            int temp = params[i];
            for (j = i - 1;j >= 0 && j > high;j --) {
                params[j + 1] = params[j];
            }
            params[high + 1] = temp;
        }
        return params;
    }
    
    /**
     * 交换排序
     *  根据两个元素关键字比较的结果,对换位置,常见的是冒泡与快速排序
     * */
    public static Integer[] buddleSort(Integer[] params) {
        if (params == null) {
            return params;
        }
        // 冒泡排序需要注意的是,比较测次数,i与j的初始值与上限问题
        for (int i = 0;i < params.length - 1;i ++) {
            boolean flag = true;
            for (int j = i + 1;j < params.length;j ++) {
                // 从小到大排序
                if (params[i] > params[j]) {
                    // 交换二者的值
                    int temp = params[i];
                    params[i] = params[j];
                    params[j] = temp;
                    flag = false;
                }
            }
            // 当前并没有发生任何交换,序列已经有序
            if (flag) {
                return params;
            }
        }
        return params;
    }
    
    /**
     * 快速排序基于分治思想
     *  快速排序最核心的一个点是,如何找到一个关键字在一群数字中的位置
     * */
    public static void quickSort(Integer[] params, int low, int high) {
        if (params == null) {
            return;
        }
        if (low < high) {
            int partition = partition(params, low, high);
            quickSort(params, low, partition - 1);
            quickSort(params, partition + 1, high);
        }        
    }
    
    // 找到某关键字在序列中的位置,最后返回下标
    public static int partition(Integer[] params, int low, int high) {
        if (params == null) {
            return 0;
        }
        // 选择第一个作为划分点
        int pivot = params[low];
        while (low < high) {
            while (low < high && params[high] >= pivot) {
                high --;
            }
            params[low] = params[high];
            while (low < high && params[low] <= pivot) {
                low ++;
            }
            params[high] = params[low];
        }
        // 获取关键字在序列中的位置,将关键字存放在该位置即可
        params[low] = pivot;
        return low;
    }
    
    /**
     * 选择排序
     *  每次选择序列中最小的元素插入排好序的序列中
     * */
    public static void selectSort(Integer[] params) {
        // 经典选择排序,不会造成元素的移动,但是元素比较的次数相对而言比较多,o(n^2)
        if (params == null) {
            return;
        }
        for (int i = 0;i < params.length;i ++) {
            int min = params[i];
            int pointer = 0;
            for (int j = i + 1;j < params.length;j ++) {
                if (params[j] < min) {
                    min = params[j];
                    pointer = j;
                    // 交换
                    int temp = params[i];
                    params[i] = min;
                    params[pointer] = temp;
                }
            }
        }
    }
    
    // 合并操作,注意都是包括最后一个单元的
    public static void merge(Integer[] params, int low, int mid, int high) {
        Integer[] copy = new Integer[params.length];
        for (int i = low;i <= high;i ++) {
            copy[i] = params[i];
        }
        int i = low;
        int j = mid + 1;
        int k = i;
        for (i = low, j = mid + 1, k = i;i <= mid && j <= high;k ++) {
            if (copy[i] <= copy[j]) {
                params[k] = copy[i ++];
            } else {
                params[k] = copy[j ++];
            }
        }
        // 插入剩余的数据,注意等号
        while (i <= mid) {
            params[k ++] = copy[i ++];
        }
        // 注意等号
        while (j <= high) {
            params[k ++] = copy[j ++];
        }
    }
    
    // 归并排序,基于分治的策略
    public static void mergeSort(Integer[] params, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(params, low, mid);
            mergeSort(params, mid + 1, high);
            merge(params, low, mid, high);
        }
    }
    
    public static void main(String[] args) {
        Integer[] wait = new Integer[]{5, 4, 1, 2, 3, 10, 12, 11};
        
        mergeSort(wait, 0, 7);
        System.out.println(Arrays.asList(wait));
    }
}