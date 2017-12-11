package com.rain.thread;

public class LockOfSynchronized {
    public synchronized void method() {
        System.out.println("do something");
    }

    public static synchronized void method(int s) {
        System.out.println("take a break, " + s);
    }

    public void method(int s, boolean flag) {
        System.out.println("take a break, " + s + flag);
    }

    public static void main(String[] args) {
        LockOfSynchronized lockOfSynchronized = new LockOfSynchronized();
        Job jobA = new Job(lockOfSynchronized);
        Job jobB = new Job(lockOfSynchronized);
        while (true) {
            new Thread(jobA).start();
            new Thread(jobB).start();
        }
    }

    private static class Job implements Runnable {
        private LockOfSynchronized lockOfSynchronized;
        public Job(LockOfSynchronized lockOfSynchronized) {
            this.lockOfSynchronized = lockOfSynchronized;
        }

        @Override
        public void run() {
            lockOfSynchronized.method(1, false);
        }
    }
}