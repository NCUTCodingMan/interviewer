package com.rain.map;

import java.lang.reflect.Field;
import java.util.*;

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

        // keySet()返回的键值可以修改
        HashMap<String, Integer> m = new HashMap<>(16);
        m.put("rain", 1);

        Set<String> keys = m.keySet();
        for (String s : keys) {
            Class clazz = s.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if ("value".equals(field.getName())) {
                    field.setAccessible(true);
                    try {
                        char[] value = (char[]) field.get(s);
                        value[0] = 'a';
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println(m.keySet());
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