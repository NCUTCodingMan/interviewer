package com.rain.thread;

/**
 * @author rain
 * created on 2018/4/6
 */
public class JoinThreadDemo {

    public static void main(String[] args) {
        Sleeper gray = new Sleeper(1500, "gray");
        Sleeper red = new Sleeper(1500, "red");

        Joiner pink = new Joiner(gray, "pink");
        Joiner black = new Joiner(red, "black");

        gray.interrupt();
    }

    static class Sleeper extends Thread {
        int duration;
        Sleeper(int duration, String name) {
            super(name);
            this.duration = duration;
            start();
        }

        @Override
        public void run() {
            System.out.println(getName() + " 开始执行任务");
            try {
                sleep(duration);
            } catch (InterruptedException e) {
                System.out.println(getName() + " 被打断了" + " 是否被打断" + isInterrupted());
            }
            System.out.println(getName() + " 已经被唤醒");
        }
    }

    static class Joiner extends Thread {
        Sleeper sleeper;
        Joiner(Sleeper sleeper, String name) {
            super(name);
            this.sleeper = sleeper;
            start();
        }

        @Override
        public void run() {
            try {
                sleeper.join();
            } catch (InterruptedException e) {
                System.out.println("打断了");
            }
            System.out.println(getName() + " join完成");
        }
    }
}