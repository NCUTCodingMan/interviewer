package com.rain.thread.sync;

import java.util.concurrent.*;

/**
 * 每位厨师应对于一个餐厅, 负责一个取餐区
 *
 * shutdown() & shutdownNow()
 *  shutdown() 调用shutdown()之后, 新的任务将不会提交给ExecutorService, 程序将在所有任务执行完毕之后尽快推出
 *  shutdownNow() 调用shutdownNow()之后, 将向ExecutorService所管理的线程发送interrupt(), 线程将会中断
 * @author rain
 * created on 2018/4/17
 */
public class Restaurant {
    static final int MAX_MEAL = 10;
    Meal meal = null;
    ExecutorService executorService = new ThreadPoolExecutor(2, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    });
    WaitPerson waitPerson = new WaitPerson(this);
    Chief chief = new Chief(this);

    Restaurant() {
        executorService.execute(waitPerson);
        executorService.execute(chief);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}

class Meal {
    /***记录当前是第几份餐*/
    private final int orderNum;

    Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal-" + orderNum;
    }
}

/**
 * 服务员
 * 等待厨师出菜
 */
class WaitPerson implements Runnable {
    private Restaurant restaurant;

    WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null) {
                        wait();
                    }
                }
                System.out.println("服务员准备获取餐饮");
                synchronized (restaurant.chief) {
                    restaurant.meal = null;
                    restaurant.chief.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被中断了...WaitPerson");
        }
    }
}

/**
 * 厨师
 * 需等待服务员传菜, 每次只做一份
 */
class Chief implements Runnable {
    private int orderNum = 0;
    private Restaurant restaurant;

    Chief(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 判断是否应该做菜
                synchronized (this) {
                    while (restaurant.meal != null) {
                        // 等待
                        wait();
                    }
                }

                if (++orderNum == Restaurant.MAX_MEAL) {
                    restaurant.executorService.shutdownNow();
                }

                // 判断是否可以获取服务员锁
                synchronized (restaurant.waitPerson) {
                    // 准备饮食
                    restaurant.meal = new Meal(orderNum);
                    System.out.println("厨师准备餐饮完毕" + restaurant.meal);
                    // 唤醒服务员
                    restaurant.waitPerson.notifyAll();
                }
            }
            // 当程序执行sleep()时, 它是可以被中断的, 并且可以捕获下面的InterruptedException
            // 当不调用下面的sleep()方法, Thread.interrupted()检测到条件满足, 不再进行循环
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("厨师被中断了");
        }
    }
}