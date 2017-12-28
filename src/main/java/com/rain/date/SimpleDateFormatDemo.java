package com.rain.date;

import java.text.SimpleDateFormat;

/**
 * created on 2017/12/28
 * SimpleDateFormat
 *  SimpleDateFormat.parse()不是线程安全的, 根本原因在于SimpleDateFormat继承DateFormat
 *  而DateFormat中包含的calendar,parse()涉及到对calendar的clear()以及初始化过程
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
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        int i = 0;
        while (i < 10) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (simpleDateFormat) {
                        try {
                            System.out.println(simpleDateFormat.parse("2017-12-28 11:00:00"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            thread.start();

            i ++;
        }
    }
}
