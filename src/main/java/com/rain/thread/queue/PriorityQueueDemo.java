package com.rain.thread.queue;

import com.rain.thread.Const;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * 之前一直有一个疑问, PriorityQueue是优先级队列, 然而debug打印队列信息时,
 * 里面的元素并不是完全有序的, 既然不是完全有序的, 那么offer()时, 为什么可以
 * 有序输出呢?
 *
 * JDK1.5所提供的PriorityQueue底层基于小根堆实现(逻辑结构), 存储结构则是数组,
 * 因此, 按下标来说, 并不是完全有序的.
 *
 * 比较重要的是add(), offer(), pull(), remove(), 这几个方法涉及到节点的插入,
 * 节点的删除, 堆的调整
 *
 * 如何基于Java实现大根堆, 传入比较器Comparator
 *
 * // Integer作为一个例子
 * compare(Integer n1, Integer n2) {
 *     return n2 - n1
 * }
 *
 * 限制
 * 1.不能传输null对象
 *
 * @author rain
 * created on 2018/4/29
 */
public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<People> priorityQueue = new PriorityQueue<>(10);
        Random random = new Random(40);
        for (int i = 0; i < Const.SMALL_LOOP; i++) {
            priorityQueue.offer(new People(random.nextInt(40)));
        }
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }

    }

    static class People implements Comparable<People> {
        private int age;

        People(int age) {
            this.age = age;
        }

        @Override
        public int compareTo(People o) {
            return this.age - o.age;
        }

        @Override
        public String toString() {
            return String.valueOf(age);
        }
    }
}