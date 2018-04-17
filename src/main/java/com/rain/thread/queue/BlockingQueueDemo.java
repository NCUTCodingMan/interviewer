package com.rain.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * blockingQueue
 * 较之于wait() & notify(), 阻塞队列带来了什么方便
 * 当队列为空时, 线程挂起, 挂起的时候可以响应中断
 * 当队列包含任务时, 线程恢复执行
 *
 * 遗留问题, 有界队列与无界队列之间的差异?
 * @author rain
 * created on 2018/4/17
 */
public class BlockingQueueDemo {
    private static final int LOOP = 5;

    public static void main(String[] args) throws Exception {
        // 无界阻塞队列
        test("LinkedBlockingQueue", new LinkedBlockingQueue<LiftOff>());
        // 有界阻塞队列
        test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
    }

    static void test(String msg, BlockingQueue<LiftOff> rockets) throws Exception {
        System.out.println(msg);
        LiftOffTask task = new LiftOffTask(rockets);
        Thread thread = new Thread(task);
        thread.start();
        for (int i = 0; i < LOOP; i++) {
            task.add(new LiftOff(5));
        }
        TimeUnit.MILLISECONDS.sleep(1000);
        thread.interrupt();
        System.out.println("程序结束");
    }

    static class LiftOffTask implements Runnable {
        private static BlockingQueue<LiftOff> rockets;

        LiftOffTask(BlockingQueue<LiftOff> rockets) {
            LiftOffTask.rockets = rockets;
        }

        void add(LiftOff liftOff) {
            try {
                rockets.put(liftOff);
            } catch (InterruptedException e) {
                System.out.println("队列不再允许添加新的任务了, 中断了");
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 移除并取队首的元素, 必要时需要等待一段时间(当前队列没有任何元素)
                    // take()由于会阻塞, 在调用thread.interrupt()时会响应中断
                    // 什么操作会响应中断, 什么操作不会响应中断, 需要分清楚
                    LiftOff rocket = rockets.take();
                    rocket.run();
                }
            } catch (InterruptedException e) {
                System.out.println("执行任务过程中被中断了");
            }
            System.out.println("ListOffTask离开run()");
        }
    }

    static class LiftOff implements Runnable {
        private int countDown = 10;
        private static int taskCount = 0;
        private final int id = taskCount++;

        LiftOff(int countDown) {
            this.countDown = countDown;
        }

        String status() {
            return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ")";
        }

        @Override
        public void run() {
            while (countDown-- > 0) {
                System.out.println(status() + Thread.currentThread().getName());
                Thread.yield();
            }
        }
    }
}