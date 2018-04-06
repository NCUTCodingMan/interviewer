package com.rain.thread;

import java.util.concurrent.*;

/**
 * 1.Executors所提供的各种创建线程池的方法中, 部分方法包含ThreadFactory形参
 * 此参数被用于创建所需要的线程资源
 * 2.可以实现该接口, 定义被创建的部分线程的属性信息
 * 3.线程在执行过程中逃逸的异常不能捕获, 这里是写了一个MyUncaughtExceptionHandler
 * 继承Thread.UncaughtExceptionHandler, 通过设置thread.setUncaughtExceptionHandler()
 * 可以为thread对象附着一个异常处理器. 从而捕获到异常
 * @author rain
 * created on 2018/4/6
 */
public class ThreadFactoryDemo {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new MyThreadFactory());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行任务的过程中发生异常了");
                throw new RuntimeException();
            }
        });
    }

    private static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            return thread;
        }
    }

    private static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName() + "\t" + e.toString());
        }
    }
}