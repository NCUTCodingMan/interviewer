package com.rain.structure.list;

import java.util.Scanner;

public class LinkedListDemo {
    
    
    private static class LinkedList {
        private Node header;
        private Node tail;
        public LinkedList() {
            this.header = null;
            this.tail = null;
        }
        
        public void createLinkedList(int data) {
            if (header == null) {
                header = new Node(data);
                header.next = null;
                tail = header;
            } else {
                tail.next = new Node(data);
                tail = tail.next;
                tail.next = null;
            }
        }
        
        /**
         * 删除单链表中倒数第k个元素,本质上时间复杂度依然为o(2n)
         * 本算法的不同之处在于删除倒数第k个元素需要找出(n - k + 1)处的元素.第一次遍历(k = k - n)
         * 第二次遍历将k变为零,即可知道前一个元素
         * @param node
         * @param k
         */
        public void getKthElementInLinkedList(Node node, int k) {
            if (node == null || k <= 0) {
                return;
            }
            Node copy = node;
            while (node != null) {
                node = node.next;
                k --;
            }
            // 删除第一个元素
            if (k == 0) {
                Node next = copy.next;
                node = next;
            } else if (k > 0) {
                throw new RuntimeException("倒数第K个元素不存在");
            } else {
                node = copy;
                while (++ k != 0) {
                    node = node.next;
                    
                }
                // 链表尾部处理
                if (node.next == tail) {
                    tail = node;
                }
                node = node.next.next;
            }
        }
        
        
        
        /**
         * 遍历链表
         */
        public void iterator() {
            Node iterator = header.next;
            while (iterator != null) {
                System.out.print(iterator.data + "\t");
                iterator = iterator.next;
            }
        }
        
    }
    
    private static class Node {
        private Node next;
        private int data;
        public Node(int data) {
            this.data = data;
        }
    }
}