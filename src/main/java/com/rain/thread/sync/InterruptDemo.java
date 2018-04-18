package com.rain.thread.sync;

import java.util.concurrent.*;

/**
 * 线程中断
 * @author rain
 * created on 2018/4/17
 */
public class InterruptDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = executorServiceFactory(1);
        executorService.execute(new Task());

        TimeUnit.SECONDS.sleep(2);

        /*
          调用shutdownNow()之后.
           1.若在while()中添加wait(), 程序将捕获到InterruptedException
           2.若while()中无wait(), 程序将在判断线程是否中断时跳出while循环
          若将此处修改为shutdown(), 则线程一直在执行线程中的循环, 一直等待线程执行完毕. 区别在于是否发送中断信号
         */
        executorService.shutdownNow();
    }

    static class Task implements Runnable {
        private int count = 0;
        @Override
        public void run() {
            try {
                synchronized (this) {
                    while (!Thread.interrupted()) {
                        count++;
                    }
                    System.out.println("线程状态变迁, 被中断了");
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + "被中断了" + "\t" + count);
            }
        }
    }

    public static ExecutorService executorServiceFactory(int corePoolSize) {
        return new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
    }
}