package com.rain.thread.restricted;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author rain
 * created on 2018/4/7
 */
public class OrnamentalGarden {
    private static final int LOOP = 5;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < LOOP; i++) {
            executorService.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancle();
        executorService.shutdown();
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sunEntrances());
    }
}

class Count {
    private int number;
    private Random random = new Random(27);

    synchronized int getValue() {
        return this.number;
    }

    synchronized int increment() {
        int temp = number;
        if (random.nextBoolean()) {
            Thread.yield();
        }
        number = ++temp;
        return number;
    }
}

class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>(12);
    private int val = 0;
    private final int id;
    /**这里的volatile可替换成synchronized*/
    private static volatile boolean cancle = false;

    Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    private int getVal() {
        return val;
    }

    @Override
    public void run() {
        while (!cancle) {
            // 入口加一
            ++val;
            System.out.println(this + " Total:" + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Entrance " + id + ": " + getVal();
    }

    public static void cancle() {
        cancle = true;
    }

    public static int getTotalCount() {
        return count.getValue();
    }

    public static int sunEntrances() {
        int sum = 0;
        for (Entrance entrance : entrances) {
            sum += entrance.getVal();
        }
        return sum;
    }
}