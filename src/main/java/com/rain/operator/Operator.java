package com.rain.operator;

import java.util.Random;
import java.util.Scanner;

public class Operator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();       
        int[] array = new int[n];        
        for (int j = 0;j < n;j ++) {
            array[j] = scanner.nextInt();
        }        
        Random seed = new Random();
        for (int k = 0;k < array.length;k ++) {
            swap(array, k, seed.nextInt(n));
        }        
        for (int l = 0;l < array.length;l ++) {
            System.out.print(array[l] + "\t");
        }       
        scanner.close();

        int i = 123;
        System.out.println(i ^ -10);

    }

    public static void parse(int n, int s, int t) {
        char[] s1 = new StringBuilder(s).reverse().toString().toCharArray();
        char[] s2 = new StringBuilder(t).reverse().toString().toCharArray();

    }

    public static int power(int n, int count) {
        int sum = 1;
        for (int i = 0;i < count;i ++) {
            sum *= n;
        }
        return sum;
    }

    public static void transform(char[] s, int n) {
        int digit = 0;
        for (char c : s) {

        }
    }

    public static void transformCharToInt(char c) {

    }
    
    public static void swap(int[] array, int source, int des) {
        int temp = array[source];
        array[source] = array[des];
        array[des] = temp;
    }
}