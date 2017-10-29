package com.rain.set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * set被设置为存放不同的元素
 */
public class TypeForSer {
    public static void main(String[] args) {
        HashSet<TreeType> treeSet = new HashSet<>();
        treeSet.add(new TreeType(1));
        treeSet.add(new TreeType(2));
        treeSet.add(new TreeType(3));
        treeSet.add(new TreeType(4));
        treeSet.add(new TreeType(1));
        System.out.println(treeSet);
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
            System.out.println(obj instanceof TreeType ? (((TreeType) obj).i == this.i ? true : false) : false);
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
