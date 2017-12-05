package com.rain.operator;

import java.util.Scanner;

public class Operator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();       
        int s = scanner.nextInt();
        int t = scanner.nextInt();

        parse(n, s, t);
    }

    private static void parse(int n, int s, int t) {
        char[] s1 = new StringBuilder(String.valueOf(Math.abs(s))).reverse().toString().toCharArray();
        char[] s2 = new StringBuilder(String.valueOf(Math.abs(t))).reverse().toString().toCharArray();

        int param1 = transform(s1, n);
        int param2 = transform(s2, n);

        int result;
        if (s < 0 && t > 0) {
            result = - (param1 + param2);
        } else if (s < 0 && t < 0) {
            result = param2 - param1;
        } else if (s > 0 && t > 0) {
            result = param1 - param2;
        } else if (s > 0 && t < 0) {
            result = param1 + param2;
        } else {
            result = 0;
        }

        System.out.println(result);
    }

    private static int power(int n, int count) {
        int sum = 1;
        if (count == 0) {
            return 1;
        }
        for (int i = 0;i < count;i ++) {
            sum *= n;
        }
        return sum;
    }

    private static int transform(char[] s, int n) {
        int digit = 0;
        for (int i = 0;i < s.length;i ++) {
            if (s[i] - 48 == 0) {

            } else {
                digit += power(n, i);
            }
        }
        return digit;
    }
}