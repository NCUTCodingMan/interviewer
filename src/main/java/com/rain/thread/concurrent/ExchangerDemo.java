package com.rain.thread.concurrent;

import java.util.List;
import java.util.concurrent.*;

/**
 * Exchanger
 *  交换, 常见于生产者与消费者, 使得更多的对象在被创建的同时被消费
 *
 * @author rain
 * created on 2018/4/29
 */
public class ExchangerDemo {
    static final int SIZE = 10;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Exchanger<List<Fat>> exchanger = new Exchanger<>();

        List<Fat> produce = new CopyOnWriteArrayList<>();
        List<Fat> consume = new CopyOnWriteArrayList<>();

        executorService.execute(new ExchangerProducer<>(exchanger, produce, Fat.class));
        executorService.execute(new ExchangeConsumer<>(exchanger, consume));

        TimeUnit.SECONDS.sleep(5);

        executorService.shutdownNow();
    }
}

class ExchangerProducer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private Class<T> clazz;

    ExchangerProducer(Exchanger<List<T>> exchanger, List<T> holder, Class<T> clazz) {
        this.exchanger = exchanger;
        this.holder = holder;
        this.clazz = clazz;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < ExchangerDemo.SIZE; i++) {
                    holder.add(clazz.newInstance());
                }
                holder = exchanger.exchange(holder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ExchangeConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;

    ExchangeConsumer(Exchanger<List<T>> exchanger, List<T> holder) {
        this.exchanger = exchanger;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            holder = exchanger.exchange(holder);
            for (T x : holder) {
                value = x;
                holder.remove(x);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value " + value);
    }
}