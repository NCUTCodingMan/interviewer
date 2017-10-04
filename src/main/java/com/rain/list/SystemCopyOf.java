package com.rain.list;

import java.util.Arrays;

public class SystemCopyOf {
    public static void main(String[] args) {
        PersonClone[] persons = new PersonClone[5];
        for (int i = 0;i < 5;i ++) {
            persons[i] = new PersonClone(i + "->" + i);
        }
        
        PersonClone[] personsAlias = new PersonClone[5];
        
        // 注意数组越界情况会出现异常
        System.arraycopy(persons, 0, personsAlias, 0, 5);
        
        // Arrays.copyof()不会产生数组越界的异常,因为它指定了新数组的大小.最多会产生截断或者null/0填充
        personsAlias = Arrays.copyOf(persons, 5);
        
        personsAlias[0].name = "roukudou";
        
        for (int i = 0;i < 5;i ++) {
            System.out.println(persons[i] == personsAlias[i]);
        }
    }
}

class PersonClone {
    public String name;
    public PersonClone(String name) {
        this.name = name;
    }
    @Override
    public String toString() {        
        return "PersonClone [name=" + name + "]";
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        PersonClone person = new PersonClone(this.name);
        return person;
    }
}