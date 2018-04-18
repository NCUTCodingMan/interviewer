package com.rain.thread.queue;

import com.rain.thread.sync.InterruptDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 吃土司
 * 吐司一共有三种状态, DRY, BUTTERED, JAMMED
 *
 * 不同的任务将处理过的吐司放入不同的队列中供其他的任务消费, 由于队列会挂起线程与唤醒线程
 * 使用阻塞队列, 业务处理比较方便, wait()与notify()会使得代码耦合度相当大, 没有使用阻塞
 * 队列好
 *
 * @author rain
 * created on 2018/4/17
 */
public class EatToast {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = InterruptDemo.executorServiceFactory(4);
        ToastQueue toastQueue = new ToastQueue(), butterQueue = new ToastQueue(), jammerQueue = new ToastQueue();
        executorService.execute(new Toaster(toastQueue));
        executorService.execute(new Butter(toastQueue, butterQueue));
        executorService.execute(new Jammer(butterQueue, jammerQueue));
        executorService.execute(new Eater(jammerQueue));

        TimeUnit.MILLISECONDS.sleep(200);
        executorService.shutdownNow();
    }

    static class Toast {
        public enum Status {
            /***吐司的状态*/
            DRY, BUTTERED, JAMMED
        }

        private Status status = Status.DRY;
        private final int id;

        private Toast(int id) {
            this.id = id;
        }

        void buffer() {
            status = Status.BUTTERED;
        }

        void jammed() {
            status = Status.JAMMED;
        }

        Status getStatus() {
            return this.status;
        }

        @Override
        public String toString() {
            return "Toast " + id + ": " + status;
        }
    }

    static class ToastQueue extends LinkedBlockingQueue<Toast> {
    }

    static class Toaster implements Runnable {
        /***制作吐司的队列*/
        private ToastQueue toastQueue;
        private int id = 0;

        Toaster(ToastQueue queue) {
            this.toastQueue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Toast toast = new Toast(id++);
                    System.out.println(toast);
                    toastQueue.put(toast);
                }
            } catch (InterruptedException e) {
                System.out.println("Toaster 被中断了");
            }
            System.out.println("Toaster 结束了");
        }
    }

    static class Butter implements Runnable {
        private ToastQueue toastQueue;
        private ToastQueue butterQueue;

        Butter(ToastQueue toastQueue, ToastQueue butterQueue) {
            this.toastQueue = toastQueue;
            this.butterQueue = butterQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Toast toast = toastQueue.take();
                    toast.buffer();
                    System.out.println(toast);
                    butterQueue.put(toast);
                }
            } catch (InterruptedException e) {
                System.out.println("Butter 被中断了");
            }
            System.out.println("Butter 结束了");
        }
    }

    static class Jammer implements Runnable {
        private ToastQueue butterQueue;
        private ToastQueue jammerQueue;

        public Jammer(ToastQueue butterQueue, ToastQueue jammerQueue) {
            this.butterQueue = butterQueue;
            this.jammerQueue = jammerQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Toast toast = butterQueue.take();
                    toast.jammed();
                    System.out.println(toast);
                    jammerQueue.put(toast);
                }
            } catch (InterruptedException e) {
                System.out.println("Jammer 被中断了");
            }
            System.out.println("Jammer 结束了");
        }
    }

    static class Eater implements Runnable {
        private ToastQueue jammerQueue;

        public Eater(ToastQueue jammerQueue) {
            this.jammerQueue = jammerQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Toast toast = jammerQueue.take();
                    if (toast.getStatus() != Toast.Status.JAMMED) {
                        System.out.println("系统出错");
                        System.exit(-1);
                    } else {
                        System.out.println("Eater 吃吐司 " + toast);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Eater 被中断了");
            }
            System.out.println("Eater 结束了");
        }
    }
}