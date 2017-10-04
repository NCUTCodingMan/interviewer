package com.rain.structure.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * 使用两个栈实现队列的功能
 * 1.出队
 * 2.入队
 * 基本思路是一个栈A用来保存原数据(先进后出), 另外一个栈B用来保存第一个栈A出栈的序列
 * 出队时,弹出栈B的栈顶元素;
 * 入队时,将元素压入栈A
 * 出入队时,注意判断stackPop是否为空
 * */
public class QueueOfStack {
    private Stack<Integer> stackPush = new Stack<Integer>();
    private Stack<Integer> stackPop = new Stack<Integer>();
    
    // 入队
    public void enQueue(Integer data) {
         stackPush.push(data);
         if (stackPop.isEmpty()) {
             while (!stackPush.isEmpty()) {
                 stackPop.push(stackPush.pop());
             }
         }
    }
    
    // 出队
    public Integer deQueue() {
        if (stackPop.isEmpty()) {
            while (!stackPush.isEmpty()) {
                stackPop.push(stackPush.pop());
            }
        }
        
        if (stackPop.isEmpty()) {
            throw new RuntimeException("栈中元素为空");
        }
        
        return stackPop.pop();
    }
    
    public static void main(String[] args) {
        QueueOfStack queueOfStack = new QueueOfStack();
        
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();
        for (int i = 0;i < n;i ++) {
            queueOfStack.enQueue(scanner.nextInt());
        }
        
        System.out.println(queueOfStack.deQueue());
        System.out.println(queueOfStack.deQueue());
        queueOfStack.enQueue(6);
        System.out.println(queueOfStack.deQueue());
        
        scanner.close();
    }
}