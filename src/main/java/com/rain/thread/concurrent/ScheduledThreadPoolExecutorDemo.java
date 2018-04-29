package com.rain.thread.concurrent;

import com.rain.thread.Const;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolExecutor
 *
 * @author rain
 * created on 2018/4/29
 */
public class ScheduledThreadPoolExecutorDemo {
    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        GreenHouse greenHouse = new GreenHouse();

        for (int i = 0; i < Const.SMALL_LOOP; i++) {
            greenHouse.schedule(new Task(), i * 1000, 2000);
        }

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        greenHouse.terminate();
    }

    private static class GreenHouse {
        private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10,
                new ThreadFactory() {
                    private final String id = "pool-thread-";
                    private int count = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName(id + count ++);
                        return thread;
                    }
                });

        /**
         * 按照一定的周期定时执行任务
         * @param event 任务
         * @param initialDelay 延迟时间
         * @param period 固定调度时间
         */
        private void schedule(Runnable event, long initialDelay, long period) {
            scheduler.scheduleAtFixedRate(event, initialDelay, period,TimeUnit.MILLISECONDS);
        }

        private void terminate() {
            scheduler.shutdownNow();
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "\t"  + sdf.format(new Date()));
            } catch (InterruptedException e) {
                System.out.println("中断");
            }
        }
    }
}