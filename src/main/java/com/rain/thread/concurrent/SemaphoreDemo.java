package com.rain.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 信号量 Semaphore
 *  信号量机制允许n个任务同时访问一个资源
 *
 * @author rain
 * created on 2018/4/29
 */
public class SemaphoreDemo {
    private static final int SIZE = 25;
    public static void main(String[] args) throws Exception {
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(new CheckoutTask<>(pool));
        }
        // 全部check out
        List<Fat> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Fat fat = pool.checkOut();
            fat.operation();
            list.add(fat);
        }

        // check out时阻塞
        Future<?> blocked = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("Check out has been interrupted");
                }
            }
        });

        TimeUnit.SECONDS.sleep(2);
        // 取消操作
        blocked.cancel(true);

        for (Fat fat : list) {
            pool.checkIn(fat);
        }

        // 忽略
        for (Fat fat : list) {
            pool.checkIn(fat);
        }

        executorService.shutdown();
    }
}

class Pool<T> {
    /***对象池的大小*/
    private int size;
    /***存放对象的容器*/
    private List<T> items = new ArrayList<>();
    /***对象是否取出的状态数组*/
    private volatile boolean[] checkOut;
    /***信号量*/
    private Semaphore available;

    /**
     * 初始化对象池
     * @param clazz 对象信息
     * @param size 大小
     */
    public Pool(Class<T> clazz, int size) {
        this.size = size;
        // 默认为false, 即当前对象未取出
        checkOut = new boolean[size];
        // 信号量的大小
        available = new Semaphore(size, true);

        for (int i = 0; i < size; i++) {
            try {
                items.add(clazz.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private synchronized T getItem() {
        // 找到第一个可用的对象, 并返回
        for (int i = 0; i < size; i++) {
            if (!checkOut[i]) {
                checkOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if (index == -1) {
            return false;
        }
        if (checkOut[index]) {
            checkOut[index] = false;
            return true;
        }
        return false;
    }

    public void checkIn(T x) {
        if (releaseItem(x)) {
            available.release();
        }
    }
}

/**
 * 创建Fat对象的开销十分大..
 */
class Fat {
    private static final int LOOP = 10000;
    private volatile double d;
    private static int count = 0;
    private final int id = count ++;

    public Fat() {
        for (int i = 0; i < LOOP; i++) {
            d += (Math.PI + Math.E) / (double) i;
        }
    }

    public void operation() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Fat id: " + id;
    }
}

class CheckoutTask<T> implements Runnable {
    private static int count = 0;
    private final int id = count ++;
    private Pool<T> pool;

    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + "check out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + "check in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}