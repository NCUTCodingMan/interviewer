package com.rain.thread.sync;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * notifyAll() & notify()会唤醒哪些线程
 * notifyAll()会唤醒与某个特定对象锁有关的线程
 *
 * wait()的调用使得当前线程阻塞, 进入等待队列
 * @author rain
 * created on 2018/4/16
 */
public class NotifyAndNotifyAll {

    private static final int LOOP = 5;

    public static void main(String[] args) throws Exception {
        ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        for (int i = 0; i < LOOP; i++) {
            pool.execute(new FirstTask());
        }
        pool.execute(new SecondTask());

        // 定时任务, 按照一定规律notify()或者notifyAll()
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private boolean prod = true;

            @Override
            public void run() {
                if (prod) {
                    System.out.println("notify()");
                    FirstTask.blocker.prod();
                    prod = false;
                } else {
                    FirstTask.blocker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400);
        TimeUnit.SECONDS.sleep(5);
        timer.cancel();

        SecondTask.blocker.prod();

        pool.shutdownNow();
    }
}

class Blocker {
    synchronized void block() {
        try {
            // 通过异常与中断的形式离开循环原理是一样的, 当while()中包含更多的代码时, 需要考虑异常与中断两种情景
            while (!Thread.interrupted()) {
                wait();
                System.out.println(Thread.currentThread().getName() + "阻塞");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了");
        }
    }

    synchronized void prod() {
        notify();
    }

    synchronized void prodAll() {
        notifyAll();
    }
}

class FirstTask implements Runnable {
    /*** 每个对象都包含一个Blocker()对象 */
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.block();
    }
}

class SecondTask implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.block();
    }
}