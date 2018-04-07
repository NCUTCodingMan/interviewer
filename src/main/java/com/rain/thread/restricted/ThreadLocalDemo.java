package com.rain.thread.restricted;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程本地存储, 阻止多个线程在同一共享资源发生冲突的第二种方式是根除对变量的共享
 * 线程本地存储是是一种自动化机制, 可以为使用相同变量的不同线程都创建不同的存储
 *
 * 后面可以看看ThreadLocal的实现原理
 * @author rain
 * created on 2018/4/7
 */
public class ThreadLocalDemo {
    private static final int LOOP = 5;

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5,
                60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                });
        for (int i = 0;i < LOOP;i ++) {
            executorService.execute(new Accessor(i));
        }
        executorService.shutdown();
    }

    static class ThreadLocalVariable {
        static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            private Random random = new Random(27);

            @Override
            protected synchronized Integer initialValue() {
                return random.nextInt(1000);
            }
        };

        static Integer getValue() {
            return threadLocal.get();
        }

        static void increment() {
            threadLocal.set(threadLocal.get() + 1);
        }
    }

    static class Accessor implements Runnable {
        private final int id;

        Accessor(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                ThreadLocalVariable.increment();
                System.out.println(this);
                Thread.yield();
            }
        }

        @Override
        public String toString() {
            return "Accessor{" +
                    "id=" + id +
                    '}' + ThreadLocalVariable.getValue();
        }
    }
}