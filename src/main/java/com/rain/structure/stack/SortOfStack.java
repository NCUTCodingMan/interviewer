package com.rain.structure.stack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

/**
 * 使用辅助栈对栈排序(从打大小或者从小到大)
 * */
public class SortOfStack {
    private Stack<Integer> source;
    private Stack<Integer> consititue;
    
    public SortOfStack() {
        source = new Stack<Integer>();
        consititue = new Stack<Integer>();
    }
    
    public void push(Integer data) {
        source.push(data);
    }
    
    /**
     * 栈排序,时间复杂度相对而言较高
     * */
    public void sort() {
        while (!source.isEmpty()) {
            Integer top = source.pop();
            if (consititue.isEmpty()) {
                consititue.push(top);
            } else {
                if (top.compareTo(consititue.peek()) >= 0) {
                    consititue.push(top);
                } else {
                    while (!consititue.isEmpty()) {
                        source.push(consititue.pop());
                    }
                    consititue.push(top);
                }
            }
        }
    }
    
    /**
     * 整型数组array, 窗口大小w, 从数组最左边滑到最右边, 窗口每次滑动一个位置
     * 求每次窗口中的最大值,下面的方式是o(n)
     * 可以采用每次搜索框中的最大值,时间复杂度是o(n*w)
     * @param array 数组
     * @param w 窗口大小
     * @return
     */
    public static Integer[] getMaxWindow(Integer[] array, int w) {
        if (array.length == 0 || w < 1 || array.length < w) {
            return null;
        }
        Integer[] result = new Integer[array.length - w + 1];
        int index = 0;
        LinkedList<Integer> qmax = new LinkedList<Integer>();
        for (int i = 0;i < array.length;i++) {
            while (!qmax.isEmpty() && array[qmax.peekLast()] <= array[i]) {
                qmax.pollLast();
            }
            qmax.addLast(i);
            if (qmax.peekFirst() == (i - w)) {
                qmax.pollFirst();
            }
            if (i >= w -1) {
                result[index ++] = array[qmax.peekFirst()];
            }
        }
        return result;
    }
    
    public static Node getMaxTree(int[] arr) {
        Node[] nodes = new Node[arr.length];
        for (int i = 0;i < arr.length;i++) {
            nodes[i] = new Node(arr[i]);
        }

        // 利用栈维护一个逆序的序列,抛出栈顶元素;若栈顶元素前面还有元素,则是他左边或者右边第一次出现的元素
        Stack<Node> stack = new Stack<Node>();
        Map<Node, Node> lBigMap = new HashMap<Node, Node>();

        // 计算每个元素左边第一个比它大的元素
        for (int i = 0;i < nodes.length;i++) {
            Node iterator = nodes[i];
            while (!stack.isEmpty() && stack.peek().data < iterator.data) {
                popStackMap(stack, lBigMap);
            }
            stack.push(iterator);
        }

        while (!stack.isEmpty()) {
            popStackMap(stack, lBigMap);
        }

        // 计算每个元素右边第一个比它大的元素
        Map<Node, Node> rBigMap = new HashMap<Node, Node>();
        for (int i = nodes.length - 1;i > -1;i--) {
            Node iterator = nodes[i];
            while (!stack.isEmpty() && stack.peek().data < iterator.data) {
                popStackMap(stack, rBigMap);
            }
            stack.push(iterator);
        }

        while (!stack.isEmpty()) {
            popStackMap(stack, rBigMap);
        }

        // 创建最大树
        Node head = null;
        for (int i = 0;i < nodes.length;i++) {
            Node current = nodes[i];
            Node left = lBigMap.get(current);
            Node right = rBigMap.get(current);
            if (left == null && right == null) {
                head = current;
            } else if (left == null) {
                if (right.left == null) {
                    right.left = current;
                } else {
                    right.right = current;
                }
            } else if (right == null) {
                if (left.left == null) {
                    left.left = current;
                } else {
                    left.right = current;
                }
            } else {
                Node parent = left.data < right.data ? left : right;
                if (parent.left == null) {
                    parent.left = current;
                } else {
                    parent.right = current;
                }
            }
        }
        return head;
    }

    public static void popStackMap(Stack<Node> stack, Map<Node, Node> map) {
        Node node = stack.pop();
        if (stack.isEmpty()) {
            // 当前元素左边没有比它大的元素
            map.put(node, null);
        } else {
            map.put(node, stack.peek());
        }
    }
    
   /**
    * 树节点类型
    * @author Administrator
    */
   private static class Node {
       private int data;
       private Node left;
       @SuppressWarnings("unused")
       private Node right;
       public Node(int data) {
           this.data = data;
           this.left = null;
           this.right = null;
       }
       @Override
       public String toString() {
           return this.data + "";
       }
   }
}