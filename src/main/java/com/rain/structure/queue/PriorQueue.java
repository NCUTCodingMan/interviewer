package com.rain.structure.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * 考虑多线程环境下,入消息,出消息
 * */
public class PriorQueue {
    private static List<Integer> queue = new ArrayList<Integer>();
    /**
     * 消息如队列,每次新增元素时重排元素
     * */
    public static void enQueue(int msg) {
        PriorQueue.queue.add(msg);
        buddleSort(queue);
    }
    
    /**
     * 消息出队列,每次移除第一个元素
     * */
    public static int deQueue() {
        if (!queue.isEmpty()) {
            return queue.remove(0);
        }
        return -1;
    }
    
    private static void buddleSort(List<Integer> queue) {
        for (int i = 0;i < queue.size() - 1;i ++) {
            for (int j = i + 1;j < queue.size();j ++) {
                if (queue.get(i) < queue.get(j)) {
                    int temp = queue.get(j);
                    queue.set(j, queue.get(i));
                    queue.set(i, temp);
                }
            }
        }
    }
    
    public static List<Integer> getQueue() {
        return PriorQueue.queue;
    }
    
    public static void main(String[] args) {
        for (int i = 0;i < 20;i ++) {
            PriorQueue.enQueue(i);
        }
        
        for (int j = PriorQueue.getQueue().size(); j >= 0;j --) {
            System.out.println(deQueue());
        }
    }
}
