package com.rain.queue;

import java.util.*;

/**
 * add()    当向一个大小有限制的队列中插入数据时,若队列已经满了,add()会抛出异常
 * offer()  队列满返回false             ***
 * poll()   队列无元素时返回null        ***
 * element() 队列无元素时抛异常
 * remove() 队列无元素时抛异常
 * peek()   队列无元素返回null                              ***
 */
public class QueueDemo {

    private static class DefinedQueue {
        private Queue<Integer> queue = new LinkedList<>();
        // 插入元素
        public void offer(Integer e) {
            queue.offer(e);
        }

        // 获取队首元素,不删除
        public Integer peek() {
            return queue.peek();
        }

        // 删除队首元素
        public Integer remove() {
            if (this.peek() != null) {
                return queue.remove();
            }
            return null;
        }

        // 队列的长度
        public int size() {
            return queue.size();
        }

        public static void main(String[] args) {
            DefinedQueue definedQueue = new DefinedQueue();
            for (int i = 0;i < 10;i ++) {
                definedQueue.offer(i);
            }
            System.out.println(definedQueue.peek());
            System.out.println(definedQueue.remove());
            System.out.println(definedQueue.size());
            System.out.println(definedQueue.peek());

            System.out.println("-------------------");

            DefinedPriorityQueue definedPriorityQueue = new DefinedPriorityQueue();
            definedPriorityQueue.random();

            Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
            List<Integer> list = new ArrayList<>(Arrays.asList(array));

            Collections.shuffle(list);
            System.out.println(list);
            for (int i = 0;i < array.length;i ++) {
                System.out.print(array[i] + "\t");
            }

            List<Integer> contents = new ArrayList<>();
            for (int i = 0;i < 10;i ++) {
                contents.add(i);
            }
        }
    }

    private static class DefinedPriorityQueue {
        private Queue<Student> queue = new PriorityQueue<>();

        public void random() {
            Random random = new Random(47);
            for (int i = 0;i < 10;i ++) {
                Student student = new Student();
                student.setAge(random.nextInt(i + 10));
                this.queue.offer(student);
            }
            this.printf();
            System.out.println(((PriorityQueue)queue).comparator());
        }

        public void printf() {
            while (queue.peek() != null) {
                System.out.println(queue.poll() + " ");
            }
        }
    }

    private static class Student implements Comparable<Student> {
        private int age;

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public int compareTo(Student o) {
            if (this.age > o.age) {
                return 1;
            } else if (this.age == o.age) {
                return 0;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return String.valueOf(this.age);
        }
    }
}