package com.rain.thread;

public class SingletonDemo {
    private static SingletonDemo singletonDemo = null;
    private SingletonDemo() {}
    private static SingletonDemo getInstance() {
        if (singletonDemo == null) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            singletonDemo = new SingletonDemo();
            System.out.println(singletonDemo.toString());
        }
        return singletonDemo;
    }

    public static void main(String[] args) {
        Thread a = new Thread() {
            @Override
            public void run() {
                SingletonDemo.getInstance();
            }
        };

        Thread b = new Thread() {
            @Override
            public void run() {
                SingletonDemo.getInstance();
            }
        };

        a.start();
        b.start();

        while (Thread.activeCount() > 2);

        System.out.println(SingletonDemo.getInstance());
    }
}