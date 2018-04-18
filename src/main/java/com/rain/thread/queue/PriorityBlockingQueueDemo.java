package com.rain.thread.queue;

import com.rain.thread.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author rain
 * created on 2018/4/18
 */
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        PriorityBlockingQueue<PriorityTask> queue = new PriorityBlockingQueue<>();
        PriorityTaskProducer producer = new PriorityTaskProducer(pool, queue);
        PriorityTaskConsumer consumer = new PriorityTaskConsumer(queue);
        pool.execute(producer);
        pool.execute(consumer);
    }
}

/**
 * 任务
 */
class PriorityTask implements Runnable, Comparable<PriorityTask> {
    private static int count = 0;
    private final int id = count++;
    /***优先级*/
    private final int priority;
    /***记录插入序列*/
    private static List<PriorityTask> sequence = new ArrayList<>();
    private static Random random = new Random(20);

    PriorityTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(PriorityTask o) {
        return this.priority < o.priority ? 1 : (this.priority > o.priority ? -1 : 0);
}

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Task => id: " + id + ", => priority: " + priority;
    }

    String summary() {
        return "id: " + id + ", => priority: " + priority;
    }

    static class EndSentinel extends PriorityTask {
        private ExecutorService executorService;
        EndSentinel(ExecutorService executorService) {
            // 最小的优先级
            super(-1);
            this.executorService = executorService;
        }

        @Override
        public void run() {
            for (PriorityTask priorityTask : sequence) {
                System.out.println(priorityTask.summary());
            }

            System.out.println("终止线程池");
            executorService.shutdownNow();
        }
    }
}

class PriorityTaskProducer implements Runnable {
    private ExecutorService e;
    private Queue<PriorityTask> queue;
    private static Random random = new Random(47);

    PriorityTaskProducer(ExecutorService e, Queue<PriorityTask> queue) {
        this.e = e;
        this.queue = queue;
    }

    @Override
    public void run() {
        // 随机赋值优先级
        for (int i = 0; i < Const.BIG_LOOP; i++) {
            queue.add(new PriorityTask(random.nextInt(10)));
            Thread.yield();
        }

        try {
            // 设置最高的优先级
            for (int i = 0; i < Const.SMALL_LOOP; i++) {
                TimeUnit.MILLISECONDS.sleep(200);
                queue.add(new PriorityTask(10));
            }

            // 按顺序赋值优先级
            for (int i = 0; i < Const.SMALL_LOOP; i++) {
                queue.add(new PriorityTask(i));
            }

            // 设置关闭线程池
            queue.add(new PriorityTask.EndSentinel(e));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PriorityTaskConsumer implements Runnable {
    private PriorityBlockingQueue<PriorityTask> queue;

    PriorityTaskConsumer(PriorityBlockingQueue<PriorityTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                queue.take().run();
            }
        } catch (InterruptedException e) {
            System.out.println("消费者线程被中断了");
        }

        System.out.println("消费者结束");
    }
}