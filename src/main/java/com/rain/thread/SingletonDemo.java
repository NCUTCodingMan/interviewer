package com.rain.thread;

public class SingletonDemo {
    private static class RecordExample {
        private int i;
        private boolean flag = false;

        private void write() {
            i = 1;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            flag = true;
        }

        private void read() {
            if (flag) {
                int temp = i * i;
                System.out.println("temp" + temp);
            } else {
                System.out.println("....");
            }
        }
    }

    public static void main(String[] args) {
        final RecordExample example = new RecordExample();

        Thread c = new Thread() {
            @Override
            public void run() {
                example.write();
            }
        };

        Thread d = new Thread() {
            @Override
            public void run() {
                example.read();
            }
        };

        c.start();
        d.start();
    }
}