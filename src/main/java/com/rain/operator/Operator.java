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
    }
    
    public static void swap(int[] array, int source, int des) {
        int temp = array[source];
        array[source] = array[des];
        array[des] = temp;
    }
}