package com.rain.structure.list;

import java.util.Stack;

public class LinkedListDemo {
    public static void main(String[] agrs) {

    }
}

class LinkedList {
    private Node header;
    private Node tail;

    public Node getHeader() {
        return this.header;
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // 采用尾插法创建单链表;使用头插法逆序一个链表
    public void createLinkedList(int data) {
        if (header == null) {
            header = new Node(data);
            tail = header;
        } else {
            Node node = new Node(data);
            node.next = null;
            tail.next = node;
            tail = tail.next;
        }
    }

    public void iterator() {
        Node iterator = header;
        while (iterator != null) {
            System.out.print(iterator.data + "\t");
            iterator = iterator.next;
        }
    }

    // 删除倒数第K个元素
    public void deleteElement(int k) {
        Node iterator = header;
        int i = 0;
        while (iterator != null) {
            i ++;
            iterator = iterator.next;
        }
        int j = 0;
        iterator = header;
        while (j < i - k - 1) {
            j ++;
            iterator = iterator.next;
        }
        if (header.next != null) {
            iterator.next = iterator.next.next;
        } else {
            header = null;
        }
    }

    // 删除链表的中间节点(采用的方法是链表的长度每增加两个,中间元素往后移动一个)
    public Node removeMidNode(Node head) {
        if (header.next == null) {
            return head;
        }
        if (header.next.next == null) {
            return header.next;
        }
        Node pre = head;
        Node cur = head.next.next;
        while (cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next;
        }
        pre.next = pre.next.next;
        return null;
    }

    // 删除a/b处的元素,如数组有三个元素,删除1/3,即第一个元素
    public Node removeByRadio(Node head, int a, int b) {
        int n = 0;
        Node cur = head;
        while (cur != null) {
            n ++;
            cur = cur.next;
        }
        n = (int) Math.ceil((double) a * n / (double) b);
        if (n == 1) {
            return header.next;
        }
        if (n > 1) {
            cur = head;
            while (--n != 1) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
        }
        return head;
    }

    /**
     * 采用头插法去逆序一个链表,嗯,写了1个小时
     * 问题在于不知道如何反转指针,核心在于将链表看做是两个链表;
     * 后面的链表直接插入到前面的链表即可
     */
    public void reverse(Node node) {
        if (node == null || node.next == null) {
            return;
        }
        Node cur = node.next;
        Node post = cur.next;
        node.next = null;
        while (cur != null) {
            cur.next = node;
            node = cur;
            cur = post;
            if (cur != null) {
                post = cur.next;
            }
        }
        this.header = node;
    }

    /**
     * 判断一个链表是否是回文结构,判断的条件与数组一致
     * 也是判断数组的长度;利用栈实现数据的逆序输出,然后
     * 一次比较是否是回文
     * @param header 链表头
     * @return 是否是会问
     */
    public boolean isMoslems(Node header) {
        Stack<Integer> stack = new Stack<>();
        int n = 0;
        Node cur = header;
        while (cur != null) {
            n ++;
            cur = cur.next;
        }

        int m;
        if (n / 2 == 0) {
            m = n / 2;
        } else {
            m = (n - 1) / 2;
        }

        int i = 0;
        cur = header;
        while (i < m) {
            stack.push(cur.data);
            cur = cur.next;
            i ++;
        }

        if (n / 2 != 0) {
            cur = cur.next;
        }

        boolean flag = true;
        while (cur != null) {
            if (stack.pop() == cur.data) {
                cur = cur.next;
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }
}