package com.rain.integer;

import java.lang.reflect.Field;

/**
 * @author rain
 * created on 2018/4/20
 */
public class SwapDemo {
    public static void main(String[] args) throws Exception {

    }

    private static void swap(Integer a, Integer b) throws Exception {
        Class<Integer> clazz = Integer.class;
        Field field = clazz.getDeclaredField("value");
        Field field2 = clazz.getDeclaredField("value");
        field.setAccessible(true);
        field2.setAccessible(true);

        Integer aa = (Integer) field.get(a);
        System.out.println("aa: " + aa);

        Integer bb = (Integer) field2.get(b);
        System.out.println("bb: " + bb);
        System.out.println("aa: " + aa);

        field.set(a, bb);
        field2.set(b, aa);
    }
}