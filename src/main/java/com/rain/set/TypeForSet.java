package com.rain.set;

import java.util.*;

/**
 * set被设置为存放不同的元素
 */
public class TypeForSet {
    public static void main(String[] args) {
        HashSet<TreeType> treeSet = new HashSet<>();
        treeSet.add(new TreeType(1));
        treeSet.add(new TreeType(2));
        treeSet.add(new TreeType(3));
        treeSet.add(new TreeType(4));
        treeSet.add(new TreeType(1));
        System.out.println(treeSet);

        Integer a = new Integer(3);
        Integer b = 3;
        int c = 3;
        System.out.println(a == b);
        System.out.println(a == c);
    }

    private static class SetType {
        protected int i;
        public SetType(int i) {
            this.i = i;
        }

        @Override
        public String toString() {
            return String.valueOf(i);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof TreeType ? (((TreeType) obj).i == this.i ? true : false) : false;
        }
    }

    private static class TreeType extends SetType implements Comparable<TreeType> {
        public TreeType(int i) {
            super(i);
        }

        @Override
        public int compareTo(TreeType o) {
            return Integer.compare(this.i, o.i);
        }
    }
}
