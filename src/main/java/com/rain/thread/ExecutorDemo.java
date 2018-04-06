package com.rain.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author rain
 * created on 2018/4/6
 */
public class ExecutorDemo {
    private static final int LOOP = 5;

    public static void main(String[] args) {
        loop(executorService("cached"), false);
        loop(executorService("fixed"), false);
        loop(executorService("single"), false);

        loop(executorService("fixed"), true);
    }

    /**
     * 使用Future获取任务执行过程中的情况
     *  Future.get()会阻塞, 直等到执行结果就绪
     *  必须使用ExecutorService.submit()执行任务
     * @param executorService 线程池
     * @param result execute or submit
     */
    private static void loop(ExecutorService executorService, boolean result) {
        if (result) {
            List<Future<String>> futures = new ArrayList<>();
            for (int i= 0;i <LOOP;i ++) {
                futures.add(executorService.submit(new TaskWithResult(i)));
            }
            for (Future<String> future : futures) {
                try {
                    System.out.println(future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    executorService.shutdown();
                }
            }
        } else {
            for (int i = 0; i < LOOP; i++) {
                executorService.execute(new LiftOff());
            }
            executorService.shutdown();
        }
    }

    private static ExecutorService executorService(String strategy) {
        ExecutorService executorService;
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        switch (strategy) {
            case "cached":
                executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                        0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), threadFactory);
                break;
            case "fixed":
                executorService = new ThreadPoolExecutor(LOOP, LOOP,
                        0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), threadFactory);
                break;
            case "scheduled":
                executorService = new ScheduledThreadPoolExecutor(LOOP, threadFactory);
                break;
            case "single":
                // 任务依次执行
                executorService = new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
                break;
            default:
                executorService = null;
                break;
        }
        return executorService;
    }

    /**
     * 实现Runnable
     */
    private static class LiftOff implements Runnable {
        int countdown = 10;
        static int taskCount = 0;
        final int id = taskCount++;

        String status() {
            return "#" + id + "(" + (countdown > 0 ? countdown : "LiftOff") + "), ";
        }

        @Override
        public void run() {
            while (countdown-- > 0) {
                System.out.print(status());
                Thread.yield();
            }
        }
    }

    /**
     * 实现Callable
     */
    private static class TaskWithResult implements Callable<String> {
        int id;

        TaskWithResult(int id) {
            this.id = id;
        }

        @Override
        public String call() {
            return "result of TaskWithResult " + id;
        }
    }
}