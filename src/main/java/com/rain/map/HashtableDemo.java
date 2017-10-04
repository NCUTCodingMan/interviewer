package com.rain.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Enumeration与Iterator
 *  二者都用来对集合进行遍历
 *  Enumeration从jdk 1.1开始,用于Hashtable与Vector
 *  Iterator从jdk 1.2开始,用于ArrayList,Hashtable...
 * */
public class HashtableDemo {
    public static Integer MAX_SIZE = 1000000;
    public static void main(String[] args) {
        Hashtable<Integer, Integer> hashTable = new Hashtable<Integer, Integer>();
        Random random = new Random();
        for (int i = 0;i < MAX_SIZE;i ++) {
            hashTable.put(i, random.nextInt(100));
        }
        
        System.out.println(System.currentTimeMillis());
        Enumeration<Integer> enumeration = hashTable.elements();
        while (enumeration.hasMoreElements()) {
            enumeration.nextElement();
        }
        
        System.out.println(System.currentTimeMillis());
        Iterator<Integer> iterator = hashTable.values().iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        
        System.out.println(System.currentTimeMillis());
        
        // 里面存放引用引用的对象是一样的
        ArrayList<SortedPerson> sortedPersons = new ArrayList<SortedPerson>();
        sortedPersons.add(new SortedPerson());
        
        ArrayList<SortedPerson> sortedPersonsAlias = new ArrayList<>(sortedPersons);
        for (int i = 0;i < sortedPersons.size();i ++){
            System.out.println(sortedPersonsAlias.get(i) == sortedPersons.get(i));
        }
        
        List<Integer> ins = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        ins.set(0, new Integer(0));
        
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put(null, 1);
        hashMap.put(null, 2);
        hashMap.put(null, 3);
        hashMap.put(null, 4);
        hashMap.put(null, 5);
    }
}

// 针对SortedMap而言,存入其中的元素要么实现Comparable接口,要么为其设置比较器Comparator
class SortedPerson implements Comparable<SortedPerson> {
    private String name;
    @Override
    public int compareTo(SortedPerson o) {
        
        return name.compareTo(o.name);
    }
}

class CustomComparator implements Comparator<SortedPerson> {
    @Override
    public int compare(SortedPerson o1, SortedPerson o2) {
        return o1.compareTo(o2);
    }
}