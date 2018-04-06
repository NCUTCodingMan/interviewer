package com.rain.thread.restricted;

import java.util.concurrent.*;

/**
 * @author rain
 * created on 2018/4/6
 */
public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircleSet circleSet = new CircleSet(1000);
    private static ExecutorService executorService = new ThreadPoolExecutor(5, Integer.MAX_VALUE,
            60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new
            ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            });

    private static class SerialCheck implements Runnable {
        @Override
        public void run() {
            while (true) {
                int serialNumber = SerialNumberGenerator.getNextSerialNumber();
                if (circleSet.contains(serialNumber)) {
                    System.out.println("存在重复的元素, 同步存在很大的问题" + serialNumber);
                    System.exit(-1);
                }
                circleSet.add(serialNumber);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(new SerialCheck());
        }
    }
}

class SerialNumberGenerator {
    private static volatile int serialNumber = 0;

    /**
     * 关于原子性的问题
     * @return 下一个序列
     */
    static int getNextSerialNumber() {
        return serialNumber++;
    }
}

class CircleSet {
    private int[] set;
    private int length;
    private int index = 0;

    CircleSet(int size) {
        set = new int[size];
        length = size;
        for (int i = 0; i < size; i++) {
            set[i] = -1;
        }
    }

    synchronized void add(int i) {
        set[index] = i;
        index = ++index % length;
    }

    synchronized boolean contains(int val) {
        for (int i = 0; i < length; i++) {
            if (set[i] == val) {
                return true;
            }
        }
        return false;
    }
}