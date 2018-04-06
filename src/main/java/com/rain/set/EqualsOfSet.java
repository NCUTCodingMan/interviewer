package com.rain.set;

import java.util.HashSet;
import java.util.Set;

/**
 * Set需要设置equals()怎么理解
 * 如何判断两个对象是否一样?
 * 基于HashMap的逻辑看 hashCode() & equals()的结果一致即可
 * 针对HashSet与LinkedHashSet而言, 存入Set的对象实现hashCode() & equals()
 * @author rain
 * created on 2018/4/5
 */
public class EqualsOfSet {

    public static void main(String[] args) {
        Set<Type> set = new HashSet<>();

        set.add(new Type(1));
        set.add(new Type(1));
        set.add(new Type(1));

        for (Type type : set) {
            System.out.println(type.getVal() + "\t" + type.hashCode());
        }
    }

    private static class Type {
        int val;

        Type(int val) {
            this.val = val;
        }

        int getVal() {
            return val;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Type && this.val == ((Type) obj).val;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(val).hashCode();
        }
    }
}