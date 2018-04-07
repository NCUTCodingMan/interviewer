package com.rain.thread.interrupt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞状态
 * IO
 * synchronized
 * sleep()
 * wait()
 * 如何解除阻塞状态
 *
 * @author rain
 * created on 2018/4/7
 */
public class InterruptDemo {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void test(Runnable runnable) throws Exception {
        Future<?> future = executorService.submit(runnable);
        TimeUnit.MILLISECONDS.sleep(100);
        future.cancel(true);
        System.out.println("main 终端被送到了" + runnable.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
        test(new SleepBlock());
        test(new IOBlock(new FileInputStream(new File("D:/trp/hibernate.reveng.xml"))));
        test(new SynchronizedBlock());
    }

}

class SleepBlock implements Runnable {
    @Override
    public void run() {
        System.out.println("Sleep 马上进入睡眠状态");
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Sleep 被中断了");
        }
        System.out.println("Sleep 离开run()");
    }
}

class IOBlock implements Runnable {
    private InputStream in;

    public IOBlock(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            System.out.println("IO 等待读入流数据");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("IO 被中断了");
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("IO 离开run()");
    }
}

class SynchronizedBlock implements Runnable {

    public synchronized void f() {
    }

    SynchronizedBlock() {
        new Thread() {
            @Override
            public void run() {
                f();
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Synchronized 尝试获取锁");
        f();
        System.out.println("Synchronized 离开run()");
    }
}