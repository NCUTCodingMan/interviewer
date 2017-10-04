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
        System.out.println(persons.contains(new PersonArrayList()));
    }
}

class PersonArrayList {
    
}
