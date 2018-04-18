package com.rain.thread.queue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列
 * 1.无界
 * 2.放置的对象需要实现Delayed接口
 * 3.对象只能到期, 即getDelay()返回为零才能被取出
 * 4.有序, 元素根据compareTo()排序
 *
 * @author rain
 * created on 2018/4/18
 */
public class DelayQueueDemo {
    private static final int SIZE = 5;

    public static void main(String[] args) {
        DelayQueue<Task> queue = new DelayQueue<>();
        long current = System.currentTimeMillis();
        queue.put(new Task(current + 5000));
        queue.put(new Task(current + 6000));
        queue.put(new Task(current + 7000));
        queue.put(new Task(current + 8000));
        queue.put(new Task(current + 100));
        System.out.println(queue);
        System.out.println("=========================================");
        for (int i = 0; i < SIZE; i++) {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static class Task implements Delayed {
        private long delay;
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Task(long delay) {
            this.delay = this.delay + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o instanceof Task) {
                return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
            } else {
                throw new RuntimeException("类型转换出现了异常");
            }
        }

        @Override
        public String toString() {
            return "Task-" + sdf.format(new Date(delay));
        }
    }
}