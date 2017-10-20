package com.rain.structure.loop;

import java.util.Scanner;

/**
 * something about dynamic programming
 */
public class DynamicProgramming {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int p = scanner.nextInt();

        System.out.println(Feibo.power(n, p));
        Feibo.step(n);
        Feibo.step(n, false);

        int a = 10;
        a >>= 1;
        System.out.println(a);

        scanner.close();
    }
}

class Feibo {
    /**
     * 到达任何一个台阶(n > 2),其走法是step(n - 2) + step(n - 1)
     * 下面的方法是从上到下计算,时间复杂度相当高o(2^n)
     * @param n 台阶数
     */
    public static int step(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            return step(n - 1) + step(n - 2);
        }
    }

    /**
     * 从下到上依次计算各个的值,时间复杂度o(n)
     * @param n
     * @param flag
     * @return
     */
    public static int step(int n, boolean flag) {
        if (flag == true) {
            return -1;
        }
        int one = 1;
        int two = 2;
        if (n == 1) {
            return one;
        }
        if (n == 2) {
            return two;
        }
        int sum = 0;
        for (int i = 3;i <= n;i ++) {
            sum = one + two;
            one = two;
            two = sum;
        }
        return sum;
    }

    public static long power(int n, int p) {
        String binary = convertToBinary(p);
        long[] set = new long[binary.length()];
        for (int i = 0;i < binary.length();i ++) {
            if (i == 0) {
                set[i] = n;
            } else {
                set[i] = set[i - 1] * set[i - 1];
            }
        }
        char[] flag = binary.toCharArray();
        long sum = 1;
        for (int i = 0;i < flag.length;i ++) {
            if (flag[i] == '1') {
                sum *= set[flag.length - 1 - i];
            }
        }
        return sum;
    }

    public static String convertToBinary(int origin) {
        StringBuffer sb = new StringBuffer();
        int result;
        while (origin != 1) {
            result = origin % 2;
            origin = origin / 2;
            sb.append(result);
        }
        sb.append(origin);
        return sb.reverse().toString();
    }

    /**
     * 求矩阵m的p次方,可以类别求一个数的幂
     * @param m
     * @param p
     * @return
     */
    public static int[][] matrixPower(int[][] m, int p) {
        // 构造单位矩阵
        int[][] res = new int[m.length][m[0].length];

        // 初始化单位矩阵(对角线全1,其他全0)
        for (int i = 0;i < res[0].length;i ++) {
            res[i][i] = 1;
        }

        for (;p != 0;p >>= 1) {

        }

        return null;
    }

    /**
     * 矩阵相乘
     * @param m1
     * @param m2
     * @return
     */
    public int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] temp = new int[m1.length][m2[0].length];
        for (int i = 0;i < m1.length;i ++) {
            for (int j = 0;j < m2[0].length;j ++) {
                for (int k = 0;k < m2.length;k ++) {
                    temp[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return temp;
    }
}