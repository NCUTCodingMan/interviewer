package com.rain.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 * @Date 2017/12/4
 */
public class LockDemo {

    private static class Resource {
        private List<Integer> list = new ArrayList<>();
        private Lock lock = new ReentrantLock();

        public void save(int n) throws InterruptedException {
            if (lock.tryLock(4, TimeUnit.SECONDS)) {
                try {
                    System.out.println(System.currentTimeMillis() + "\t" + Thread.currentThread().getName() + "获取了锁");
                    long now = System.currentTimeMillis();
                    while (System.currentTimeMillis() - now < 5000) {

                    }
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + "放弃锁");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Resource resource = new Resource();

        new Thread(() -> {
            try {
                resource.save(n);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "线程被中断了");
            }
        }).start();
        Thread t = new Thread(() -> {
            try {
                resource.save(n);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "线程被中断了");
            }
        });
        t.start();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        t.interrupt();

        scanner.close();
    }
}
