package com.rain.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 1.使用反射带来的优点与缺点是什么?
 * 2.框架中使用反射的例子,举举看
 */
public class Reflect {
    public static void main(String[] args) {
        Person person = new Person();
        try {
            Reflect.parse(person);
        } catch (Exception e) {

        }

        int a = 123;

        System.out.println(~a);
    }
    public static void parse(Object obj) throws Exception {
        Class<? extends Object> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(obj);
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println(annotation.annotationType());
            }
            System.out.println(fieldName + "\t" + fieldValue);
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + "\t" + method.invoke(obj));
        }
    }

    static class Person {
        private int age;
        private String name;
        private String address;
        private String mobile;

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", mobile='" + mobile + '\'' +
                    '}';
        }
    }
}