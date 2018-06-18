package com.rain.date;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

/**
 * @author rain
 * created on 2017/12/28
 * SimpleDateFormat
 *  SimpleDateFormat.是线程安全的, 根本原因在于SimpleDateFormat继承DateFormat
 *  而DateFormat中包含的calendaparse()不r,parse()涉及到对calendar的clear()以及初始化过程
 *  并且不同线程共用同一个calendar,因此不是线程安全的
 *
 * 解决的思路
 * (1)加锁
 * (2)每个线程的run()中新建SimpleDateFormat
 * (3)使用ThreadLocal
 *
 * 链接 http://ifeve.com/notsafesimpledateformat/
 */
public class SimpleDateFormatDemo {
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static final int COUNT = 10;

    public static void main(String[] args) {
        int i = 0;
        while (i < COUNT) {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(threadLocal.get().parse("2017-12-28 11:00:00"));

                        Thread thread = Thread.currentThread();
                        Field[] fields = thread.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            try {
                                System.out.println(field.getName() + "\t" + field.get(thread));
                                String name = "threadLocals";
                                if (field.getName().equals(name)) {
                                    Object object = field.get(thread);
                                    System.out.println(object);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            i ++;
        }
    }

}
