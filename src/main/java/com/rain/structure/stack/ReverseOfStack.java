package com.rain.structure.stack;

import java.util.Scanner;
import java.util.Stack;

public class ReverseOfStack {
    // 递归获取栈底元素,并不打散之前的顺序
    public static Integer getTheLastElementOfStack(Stack<Integer> source) {
        if (source.isEmpty()) {
            throw new RuntimeException();
        }
        Integer top = source.pop();
        if (!source.isEmpty()) {
            Integer result = getTheLastElementOfStack(source);
            source.push(top);
            return result;
        } else {
            return top;
        }
    }
    
    // 递归获取栈底元素,并将该原数放入原栈;在递归的过程中自动记录上一步出栈的记录
    public static void reverse(Stack<Integer> source) {
        if (!source.isEmpty()) {
            Integer buttom = getTheLastElementOfStack(source);
            reverse(source);
            source.push(buttom);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Stack<Integer> source = new Stack<Integer>();
        while (scanner.hasNext()) {
            int data = scanner.nextInt();
            if (data == 0) {
                break;
            }
            source.push(data);
        }
        
        reverse(source);
        System.out.println(source);
        scanner.close();
    }
}