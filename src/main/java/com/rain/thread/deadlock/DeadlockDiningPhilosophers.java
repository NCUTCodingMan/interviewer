package com.rain.thread.deadlock;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 哲学家就餐, 关于死锁的问题
 * 死锁产生的必要条件
 *  1.互斥
 *  2.保持等待
 *  3.非剥夺
 *  4.循环等待
 * 破坏其中的一个条件就可以破除死锁
 *
 * @author rain
 * created on 2018/4/18
 */
public class DeadlockDiningPhilosophers {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 滞留时间
        int pendingFactor = scanner.nextInt();
        // 数组大小
        int size = scanner.nextInt();
        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 初始化筷子的个数
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick();
        }
        // 初始化哲学家任务
        for (int i = 0; i < size; i++) {
            // 取模运算
            if (i < (size - 1)) {
                executorService.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, pendingFactor));
            } else {
                // 除最后一位哲学家之外, 其他的哲学家都是先拿左边的筷子, 再拿右边的筷子
                executorService.execute(new Philosopher(chopsticks[0], chopsticks[i], i, pendingFactor));
            }
        }

//        TimeUnit.SECONDS.sleep(2);
//        executorService.shutdownNow();
    }

    /**
     * 筷子
     */
    static class Chopstick {
        /***筷子的状态, 默认筷子没有被其他人取. 当status为false时, 可取*/
        private boolean status = false;

        /**
         * 取筷子
         */
        synchronized void take() {
            try {
                while (status) {
                    wait();
                }
                status = true;
            } catch (InterruptedException e) {
                System.out.println("取筷子操作被打断了");
            }
        }

        /**
         * 放下筷子
         */
        synchronized void drop() {
            status = false;
            notifyAll();
        }
    }

    /**
     * 哲学家就餐线程
     */
    static class Philosopher implements Runnable {
        /***左边筷子*/
        Chopstick left;
        /***右边筷子*/
        Chopstick right;
        private final int id;
        /***滞留时间因子*/
        private final int pendingFactor;

        private void pause() throws InterruptedException {
            if (pendingFactor != 0) {
                TimeUnit.MILLISECONDS.sleep(new Random(47).nextInt(pendingFactor * 20));
            }
        }

        Philosopher(Chopstick left, Chopstick right, int id, int pendingFactor) {
            this.left = left;
            this.right = right;
            this.id = id;
            this.pendingFactor = pendingFactor;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(this + " is thinking");
                    pause();
                    System.out.println(this + " 准备拿左边的筷子");
                    left.take();
                    System.out.println(this + " 已拿左边的筷子");
                    System.out.println(this + " 准备拿右边的筷子");
                    right.take();
                    System.out.println(this + " 已拿右边的筷子");
                    pause();
                    System.out.println(this + " 哲学家已吃完饭了");
                    right.drop();
                    left.drop();
                }
            } catch (InterruptedException e) {
                System.out.println("Philosopher 被中断了");
            }
        }

        @Override
        public String toString() {
            return "Philosopher " + id;
        }
    }
}