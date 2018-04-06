package com.rain.thread.restricted;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类
 *
 * @author rain
 * created on 2018/4/6
 */
public class AtomicDemo {
    private static final int LOOP = 5;

    private static class SerialNumber {
        private static AtomicInteger serialNumber = new AtomicInteger(0);

        public static int getNextSerialNumber() {
            return serialNumber.getAndAdd(2);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(5, Integer.MAX_VALUE,
                60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new
                ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                });

        for (int i = 0; i < LOOP; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        SerialNumber.getNextSerialNumber();
                    }
                }
            });
        }
        executorService.shutdown();
    }
}