package com.rain.thread.sync;

import java.util.concurrent.*;

/**
 * 打蜡与抛光
 * @author rain
 * created on 2018/4/16
 */
public class WaxOfCar {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 60L,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                if (r instanceof WaxOn) {
                    thread.setName("TaskOfWaxOn");
                } else {
                    thread.setName("TaskOfWaxOff");
                }
                return thread;
            }
        });
        Automobile automobile = new Automobile();
        pool.execute(new TaskOfWaxOff(automobile));
        pool.execute(new TaskOfWaxOn(automobile));

        TimeUnit.MILLISECONDS.sleep(2000);
        // 隐式调用所有它所控制线程的interrupt()
        pool.shutdownNow();
    }
}


/**
 * 汽车
 *  主要包括两类操作, 一类具体操作, 一类等待是否能够进行具体操作
 * while(true)目前想的不是很清楚
 */
class Automobile {
    /*** 是否可以打蜡, 默认为true*/
    private boolean waxOn = true;

    /**
     * 打蜡
     */
    synchronized void waxOn() {
        // 将状态置为false
        waxOn = false;
        System.out.println("正在打蜡");
        notifyAll();

    }

    /**
     * 抛光
     */
    synchronized void waxOff() {
        waxOn = true;
        System.out.println("正在抛光");
        notifyAll();
    }

    /**
     * 等待打蜡结束
     */
    synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn) {
            wait();
        }
    }

    /**
     * 等待抛光结束
     */
    synchronized void waitForBuffering() throws InterruptedException {
        while (!waxOn) {
            wait();
        }
    }
}

class TaskOfWaxOn implements Runnable {
    private Automobile automobile;

    TaskOfWaxOn(Automobile automobile) {
        this.automobile = automobile;
    }

    @Override
    public void run() {
        try {
            // 检测当前线程是否被打断
            while (!Thread.interrupted()) {
                automobile.waxOn();
                automobile.waitForBuffering();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "已中断");
        }
    }
}

class TaskOfWaxOff implements Runnable {
    private Automobile automobile;

    TaskOfWaxOff(Automobile automobile) {
        this.automobile = automobile;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                automobile.waitForWaxing();
                automobile.waxOff();
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "已中断");
        }
    }
}