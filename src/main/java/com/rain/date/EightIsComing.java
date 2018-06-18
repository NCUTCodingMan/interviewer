package com.rain.date;

import java.util.concurrent.*;

/**
 * @author source
 * @date 2018/5/7
 */
public class EightIsComing {
    private static final int SIZE = 5;

    public static void main(String[] args) {
        ExecutorService pool = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 60L,
                TimeUnit.MICROSECONDS, new SynchronousQueue<>(), (Runnable r) -> {
            Thread thread = new Thread(r);
            return thread;
        });

        for (int i = 0; i < SIZE; i++) {
            pool.execute(new Task());
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdownNow();
    }

    static class Task implements Runnable {
        private static int count = 0;
        private final int id = count++;

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "\t" + id);
        }
    }
}
