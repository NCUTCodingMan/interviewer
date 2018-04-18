package com.rain.thread.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CountDownLatch
 *  用来同步一个或者多个任务, 其在初始化时可指定一个数值, 该值在减小到 0之前, 任何在这个对象上
 *  调用wait()的方法都将阻塞; 与之相反的是, 其他任务在执行完任务之后, 调用这个对象上的countDown()
 *  来减小初始值的大小
 *
 * 此工具类可用于同步一个或者多个任务, 强制它们等待由其他任务执行的一组操作完成
 *
 * @author rain
 * created on 2018/4/18
 */
public class CountDownLatchDemo {
    private static final int LOOP = 10;
    private static final int SIZE = 2;
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(LOOP);
        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < SIZE; i++) {
            pool.execute(new WaitTask(latch));
        }

        for (int i = 0; i < LOOP; i++) {
            pool.execute(new TaskPartiton(latch));
        }

        pool.shutdown();
    }

    static class TaskPartiton implements Runnable {
        private static int count = 0;
        private final int id = count++;
        private CountDownLatch latch;

        TaskPartiton(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            doWork();
            System.out.println("latch-" + latch.getCount());
            // 不会造成方法阻塞
            latch.countDown();
        }

        void doWork() {
            try {
                TimeUnit.MILLISECONDS.sleep(new Random(47).nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "TaskPartition-" + id;
        }
    }

    static class WaitTask implements Runnable {
        private static int count = 0;
        private final int id = count++;
        private CountDownLatch latch;

        public WaitTask(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println(this + "等待");
                // 造成当前的方法阻塞, 直等到latch的值为0
                latch.await();
                System.out.println(this + "等待结束");
            } catch (InterruptedException e) {
                System.out.println("WaitTask 被中断了");
            }
        }

        @Override
        public String toString() {
            return "WaitTask-" + id;
        }
    }
}