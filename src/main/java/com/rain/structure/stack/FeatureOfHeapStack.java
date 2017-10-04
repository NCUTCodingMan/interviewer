package com.rain.structure.stack;

import java.util.Scanner;
import java.util.Stack;

public class FeatureOfHeapStack {    
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        StackOfMaxAndMinHeap stackOfMaxAndMinHeap = new StackOfMaxAndMinHeap();
        int n = scanner.nextInt();
        for (int i = 0;i < n;i ++) {
            stackOfMaxAndMinHeap.push(scanner.nextInt());
        }
        
        stackOfMaxAndMinHeap.pop();
        stackOfMaxAndMinHeap.pop();
        stackOfMaxAndMinHeap.pop();
        stackOfMaxAndMinHeap.pop();
        System.out.println(stackOfMaxAndMinHeap.getMin() + "," + stackOfMaxAndMinHeap.getMax());
        
        scanner.close();
    }
}

/**
 * 具有如下特征的栈, 以下所有操作均在o(1)时间内完成
 * 1.pop()
 * 2.push()
 * 3.getMax()
 * 4.getMin()
 * 使用辅助栈来完成,一个存放最大值,一个存放最小值;注意入栈与出栈时,其他栈的出栈顺序
 * */
class StackOfMaxAndMinHeap {
    private Stack<Integer> stackData;
    private Stack<Integer> stackMin;
    private Stack<Integer> stackMax;
    
    public StackOfMaxAndMinHeap() {
        stackData = new Stack<Integer>();
        stackMax = new Stack<Integer>();
        stackMin = new Stack<Integer>();
    }
    
    public boolean isEnpty() {
        return stackData.isEmpty();
    }
    
    public void push(Integer data) {
        stackData.push(data);
        
        // 所有的第一个元素都会存放在三个栈中
        if (stackMin.isEmpty()) {
            stackMin.push(data);
        } else {
            if (data.compareTo(stackMin.peek()) <= 0) {
                stackMin.push(data);
            }
        }        
        
        if (stackMax.isEmpty()) {
            stackMax.push(data);
        } else {
            if (data.compareTo(stackMax.peek()) >= 0) {
                stackMax.push(data);
            }
        }        
    }
    
    public Integer pop() {
        if (!stackData.isEmpty()) {
            Integer top = stackData.pop();
            if (!stackMin.isEmpty() && stackMin.peek().equals(top)) {
                stackMin.pop();
            }
            
            if (!stackMax.isEmpty() && stackMax.peek().equals(top)) {
                stackMax.pop();
            }
            return top;
        }
        return -1;
    }
    
    public Integer getMin() {
        if (stackMin.isEmpty()) {
            throw new RuntimeException();
        }
        return stackMin.peek();
    }
    
    public Integer getMax() {
        if (stackMax.isEmpty()) {
            throw new RuntimeException();
        }
        return stackMax.peek();
    }
}