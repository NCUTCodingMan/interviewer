package com.rain.lock;

import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    public interface Way {
        void read(Thread currentThread);
    }

    // 使用synchronized方式并行读
    public static class SynchronizedWay implements Way {
        public synchronized void read(Thread currentThread) {
            System.out.println(currentThread.getName() + "准备    进行读操作");
            long current = System.currentTimeMillis();
            while (System.currentTimeMillis() - current < 1) {
                System.out.println(currentThread.getName() + "开始    进行读操作");
            }
            System.out.println(currentThread.getName() + "结束    读操作");
        }
    }

    public static class ReentrantReadWriteLockWay implements Way {
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        public void read(Thread currentThread) {
            try {
                reentrantReadWriteLock.readLock().lock();
                System.out.println(currentThread.getName() + "准备    进行读操作");
                long current = System.currentTimeMillis();
                while (System.currentTimeMillis() - current < 1) {
                    System.out.println(currentThread.getName() + "开始    进行读操作");
                }
                System.out.println(currentThread.getName() + "结束    读操作");
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }
        }
    }

    private static Way tactics(int n) {
        Way way;
        switch (n % 2) {
            case 0:
                way = new SynchronizedWay();
                break;
            default:
                way = new ReentrantReadWriteLockWay();
                break;
        }
        return way;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Way way = tactics(scanner.nextInt());

        new Thread(() ->
            way.read(Thread.currentThread())
        ).start();

        new Thread(() ->
            way.read(Thread.currentThread())
        ).start();

        scanner.close();
    }
}
