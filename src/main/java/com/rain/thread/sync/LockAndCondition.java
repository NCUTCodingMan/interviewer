package com.rain.thread.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 显示的创建Lock & Condition来控制线程之间的通讯
 *
 * @author rain
 * created on 2018/4/17
 */
public class LockAndCondition {

    public static void main(String[] args) throws Exception {
        Car car = new Car();

        ExecutorService executorService = InterruptDemo.executorServiceFactory(2);
        executorService.execute(new WaxOff(car));
        executorService.execute(new WaxOn(car));

        TimeUnit.MILLISECONDS.sleep(20);

        executorService.shutdownNow();
    }

    static class Car {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        boolean waxOn = false;

        void waxOn() {
            try {
                lock.lock();
                // 打蜡
                System.out.println("打蜡");
                waxOn = true;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        void waxOff() {
            try {
                lock.lock();
                // 抛光
                System.out.println("抛光");
                waxOn = false;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        /**
         * 等待打蜡结束
         */
        void waitForWaxing() {
            try {
                lock.lock();
                while (!waxOn) {
                    condition.await();
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "线程被中断了");
            } finally {
                lock.unlock();
            }
        }

        /**
         * 等待抛光结束
         */
        void waitForBuffering() {
            try {
                lock.lock();
                while (waxOn) {
                    condition.await();
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "线程被中断了");
            } finally {
                lock.unlock();
            }
        }
    }

    static class WaxOn implements Runnable {
        Car car;

        WaxOn(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                car.waxOn();
                car.waitForBuffering();
            }
        }
    }

    static class WaxOff implements Runnable {
        Car car;

        WaxOff(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                car.waitForWaxing();
                car.waxOff();
            }
        }
    }
}