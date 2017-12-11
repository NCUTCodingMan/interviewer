package com.rain.thread;

public class SingletonDemo {
    private static SingletonDemo singletonDemo = null;
    private SingletonDemo() {}
    public static SingletonDemo getInstance() {
        if (singletonDemo == null) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            singletonDemo = new SingletonDemo();
        }
        return singletonDemo;
    }

    public static void main(String[] args) {
        Thread a = new Thread() {
            @Override
            public void run() {

            }
        };
    }
}
