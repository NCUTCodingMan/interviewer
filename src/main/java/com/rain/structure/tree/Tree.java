package com.rain.structure.tree;

import java.util.Scanner;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Tree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BinaryTree binaryTree = new BinaryTree();
        while (scanner.hasNext()) {
            int data = scanner.nextInt();
            if (data == 0) {
                break;
            }
            binaryTree.createBinaryTree(binaryTree.getRoot(), data);
        }

        binaryTree.preOrder(binaryTree.getRoot());

        scanner.close();
    }
}

class BinaryTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    private class Node {
        private int data;
        private Node left;
        private Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    public void createBinaryTree(Node node, int data) {
        if (root == null) {
            root = new Node(data);
        } else {
            if (data > node.data) {
                if (node.right == null) {
                    node.right = new Node(data);
                } else {
                    createBinaryTree(node.right, data);
                }
            } else {
                if (node.left == null) {
                    node.left = new Node(data);
                } else {
                    createBinaryTree(node.left, data);
                }
            }
        }
    }

    /**
     * 递归先序遍历
     *
     * @param root
     */
    public void preOrder(Node root) {
        if (root != null) {
            System.out.print(root.data + "\t");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    /**
     * 非递归先序遍历
     *
     * @param root
     */
    public void preOrderBasedOnStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        stack.add(root);
        while (root != null || !stack.isEmpty()) {
            System.out.print(root.data + "\t");
            if (root.left != null) {
                root = root.left;
                stack.push(root);
            } else {
                root = stack.pop();
                root = root.right;
                stack.push(root);
            }
        }
    }

    /**
     * 递归中序遍历
     *
     * @param root
     */
    public void midOrder(Node root) {
        if (root != null) {
            midOrder(root.left);
            System.out.print(root.data + "\t");
            midOrder(root.right);
        }
    }

    /**
     * 非递归中序遍历与先序类似,只是输出语句的位置不一样
     */
    public void midOrderBasedOnStack(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        while (root != null || !stack.isEmpty()) {
            if (root.left != null) {
                root = root.left;
                stack.push(root);
            } else {
                root = stack.pop();
                System.out.print(root.data + "\t");
                root = root.right;
                stack.push(root);
            }
        }
    }

    /**
     * 递归后序遍历
     *
     * @param root
     */
    public void postOrder(Node root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.println(root.data);
        }
    }

    /**
     * 层次遍历以及获取到树的宽度
     */
    public void level(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        int width = 1;
        while (queue.size() != 0) {
            int length = queue.size();
            // 将一个层次的所有元素全部出栈
            while (length > 0) {
                Node node = queue.poll();
                System.out.print(node.data);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                length--;
            }
            width = Math.max(width, queue.size());
        }
        System.out.println("\n" + "max width: " + width);
    }

    /**
     * 获取树的深度
     * 同样是基于分治的策略操作
     */
    public int depth(Node root) {
        if (root == null) {
            return 0;
        }
        int left = depth(root.left);
        int right = depth(root.right);
        return left > right ? left + 1 : right + 1;
    }
}