package com.rain.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier
 *  CyclicBarrier与CountDownLatch之间的区别在于是否可以重复设置"值"
 *  CountDownLatch只能设置一次, 而CyclicBarrier可以设置多次
 *
 * @author rain
 * created on 2018/4/18
 */
public class CyclicBarrierDemo {
    private final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    private CyclicBarrierDemo(int nHorses) {
        // Runnable, 当所有线程到达栅栏处时, 执行的操作
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    builder.append("=");
                }
                System.out.println(builder);
                for (Horse horse : horses) {
                    System.out.println(horse.trace());
                }
                // 是否有马已经达到终点, 若有, 停止所有线程
                for (Horse horse : horses) {
                    if (horse.getScore() >= FINISH_LINE) {
                        System.out.println(horse + "win");
                        executorService.shutdownNow();
                        return;
                    }
                }
            }
        });

        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        new CyclicBarrierDemo(7);
    }

    static class Horse implements Runnable {
        private static int count = 0;
        private final int id = count++;
        /***当前马所获取的分数*/
        private int score = 0;
        private static Random random = new Random(47);
        private static CyclicBarrier barrier;

        Horse(CyclicBarrier barrier) {
            Horse.barrier = barrier;
        }

        /**
         * 增加同步控制
         * @return 当前分数
         */
        synchronized int getScore() {
            return this.score;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 同步
                    synchronized (this) {
                        score = score + random.nextInt(3);
                    }
                    // 线程到达此处之后将会阻塞一段时间, 之后当所有线程都到达栅栏处时, 接着运行
                    barrier.await();
                }
            } catch (InterruptedException e) {
                System.out.println("中断");
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String toString() {
            return "horse-" + id;
        }

        String trace() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < getScore(); i++) {
                builder.append("*");
            }
            builder.append(id);
            return builder.toString();
        }
    }
}