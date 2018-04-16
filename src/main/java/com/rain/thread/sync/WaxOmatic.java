package com.rain.thread.sync;

import java.util.concurrent.*;

/**
 * wait() & notify()
 * 线程之间的状态转化十分复杂, 线程调用不同的方法将会导致线程切换到不同的状态
 *
 * @author rain
 * created on 2018/4/14
 */
public class WaxOmatic {
    private Car car;
    /*** 是否已经抛光 */
    private boolean flag = true;

    void tuCar() {
        System.out.println("1");
    }

    void paoCar() {
        System.out.println("0");
    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }

    boolean isFlag() {
        return this.flag;
    }

    public static void main(String[] args) throws Exception {
        WaxOmatic waxOmatic = new WaxOmatic();
        waxOmatic.car = new Car();

        WaxOn waxOn = new WaxOn(waxOmatic);
        WaxOff waxOff = new WaxOff(waxOmatic);

        ExecutorService executorService = new ThreadPoolExecutor(2, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                });
        executorService.execute(waxOn);
        executorService.execute(waxOff);

        Thread.sleep(500);
        executorService.shutdownNow();
    }
}

class Car {
}

class WaxOn implements Runnable {
    private WaxOmatic waxOmatic;

    public WaxOn(WaxOmatic waxOmatic) {
        this.waxOmatic = waxOmatic;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (waxOmatic) {
                if (waxOmatic.isFlag()) {
                    waxOmatic.tuCar();
                    waxOmatic.setFlag(false);
                    waxOmatic.notify();
                } else {
                    try {
                        // 放弃waxOmatic的锁
                        waxOmatic.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class WaxOff implements Runnable {
    private WaxOmatic waxOmatic;

    WaxOff(WaxOmatic waxOmatic) {
        this.waxOmatic = waxOmatic;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (waxOmatic) {
                if (!waxOmatic.isFlag()) {
                    waxOmatic.paoCar();
                    waxOmatic.setFlag(true);
                    waxOmatic.notify();
                } else {
                    try {
                        waxOmatic.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}