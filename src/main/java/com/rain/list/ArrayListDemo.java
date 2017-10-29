package com.rain.list;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListDemo {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        
        String[] s1 = {"1","1","1","1","1","1","1","1","1","1"};
        String[] s2 = new String[20];
        
        s2 = Arrays.copyOf(s1, 20);
        System.out.println(s2);
    
        ArrayList<PersonArrayList> persons = new ArrayList<PersonArrayList>();
        persons.add(new PersonArrayList());
        persons.add(new PersonArrayList());
        persons.add(new PersonArrayList());
        persons.add(new PersonArrayList());

        Object[] p = (Object[])persons.toArray();
        ((PersonArrayList)p[0]).setName("1234");
        System.out.println(((PersonArrayList)p[0]).getName() + "\t" + persons.get(0).getName());


    }

    private static class Clone {
        private String content = "Hello";
    }
}

class PersonArrayList {
    private String name = "hello world";
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}