package com.rain.thread.restricted;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rain
 * created on 2018/4/6
 */
public class EvenGenerator extends AbstractIntGenerator {
    private int begin = 0;
    private Lock lock = new ReentrantLock();

    @Override
    int next() {
        lock.lock();
        try {
            // ++ 并不是原子性操作
            ++ begin;
            ++ begin;
        } finally {
            lock.unlock();
        }
        return begin;
    }

    private static void test(AbstractIntGenerator generator, int count) {
        System.out.println("按C退出程序");
        // 此构造方法需要再研究, corePoolSize设置为零时会发生什么
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            executorService.execute(new EvenChecker(generator, i));
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        EvenGenerator.test(new EvenGenerator(), 10);
    }
}

abstract class AbstractIntGenerator {
    private volatile boolean canceled = false;

    /**
     * 生成下一位数字
     *
     * @return 下一位数字
     */
    abstract int next();

    void cancel() {
        canceled = true;
    }

    boolean isCanceled() {
        return canceled;
    }
}

class EvenChecker implements Runnable {
    private AbstractIntGenerator generator;
    private final int id;

    EvenChecker(AbstractIntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        while (!generator.isCanceled()) {
            int digit = generator.next();
            if (digit % 2 != 0) {
                System.out.println(digit + " 不是偶数");
                generator.cancel();
            }
        }
    }
}