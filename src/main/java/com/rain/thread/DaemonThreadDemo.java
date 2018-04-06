package com.rain.thread;

import java.util.concurrent.TimeUnit;

/**
 * 后台线程
 *  1.当所有的非后台线程都结束时, 程序终止, 同时会杀死所有的后台线程
 *  2.一个线程是否是后台线程可以通过isDaemon()判断
 *  3.通过setDaemon(true)设置线程为后台线程
 *  4.finally中的代码, 程序终止时后台线程并不会执行(保持一致性)
 * @author rain
 * created on 2018/4/6
 */
public class DaemonThreadDemo {
    private static final int LOOP = 10;
    public static void main(String[] args) throws Exception {
        for (int i = 0;i < LOOP;i ++) {
            Thread thread = new Thread(new Daemon());
            thread.setDaemon(true);
            thread.start();
        }
        System.out.println("all Daemon start");
        TimeUnit.MILLISECONDS.sleep(20);
    }

    private static class Daemon implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println(Thread.currentThread() + "\t" + this);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}