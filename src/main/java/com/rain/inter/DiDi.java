package com.rain.inter;

import java.util.Scanner;
import java.util.Stack;

public class DiDi {
    /**
     * 最大连续子序列
     * @param source 原数组
     * @return 最大连续子序列
     */
    public static int getSumOfMaxSequence(int[] source) {
        if (source.length == 0) {
            return 0;
        }
        int max = source[0];
        int temp = 0;
        int i = 0;
        int j = 0;
        int k = 0;

        // 三层for循环的计算量太大, 容易导致程序运行时间超限制
        for (i = 0; i < source.length - 1; i++) {
            for (j = i; j < source.length; j++) {
                for (k = i; k <= j; k++) {
                    temp += source[k];
                }
                if (max < temp) {
                    max = temp;
                }
                temp = 0;
            }
        }

        if (source[source.length - 1] > max) {
            max = source[source.length - 1];
        }

        return max;
    }

    /**
     * 只进行一次循环即可解决问题, 在和为负数时, 从下一个元素重新开始计算 这里可以使用动态规划的思想来分析, 采用递归的方式来解决.
     * @param source
     * @param flag 重载标志
     * @return
     */
    public static int getSumOfMaxSequence(int[] source, boolean flag) {
        if (source.length == 0) {
            return 0;
        }

        int sum = 0;
        int max = 0;
        for (int i = 0; i < source.length; i++) {
            if (sum < 0) {
                sum = source[i];
            } else {
                sum += source[i];
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max;
    }

    /**
     * 进制转化,应用短除法的 + 栈来实现
     * @param n 原数字
     * @param destionation 进制
     * @return 转换后的数字
     */
    public static String swicthDigit(int n, int destionation) {
        int m = 0;
        int left = 0;
        Stack<Integer> result = new Stack<Integer>();
        while (n != 0) {
            m = n / destionation; // 商
            left = n - m * destionation; // 余
            result.add(left);
            n = m;
        }
        StringBuffer sb = new StringBuffer();
        int length = result.size();
        while (!result.isEmpty()) {
            Integer i = result.pop();
            if (i < 10) {
                // 处理符号,仅仅放行第一个
                if (i < 0 && result.size() != (length - 1)) {
                    i = -i;
                }
                // 对负数的处理,加上55对其转化为相应的ascii码
                if (Math.abs(i) >= 10) {
                    sb.append((char)(i.intValue() + 55));
                    continue;
                }
                sb.append(i.toString());
            } else if (Math.abs(i) >= 10) {
                sb.append((char)(i.intValue() + 55));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int destionation = scanner.nextInt();

        System.out.println(swicthDigit(n, destionation));
        
        scanner.close();       
    }
}