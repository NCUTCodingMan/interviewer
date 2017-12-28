package com.rain.thread;

public class SingletonDemo {
    private static class RecordExample {
        private int i;
        // 当flag未声明为volatile时,程序的输出有可能为"....",出现此的原因在于flag的改变对其他线程不可见
        private volatile boolean flag = false;

        private void write() {
            i = 1;
            flag = true;
            // 模拟在进行计算,使线程工作内存中flag的值不能立即刷新到主内存中
            int j = 0;
            while (j ++ < 100000000);
        }

        private void read() {
            // 根据flag的值判断之前是否已经执行过write()
            if (flag) {
                int temp = i * i;
                System.out.println("temp:" + temp);
            } else {
                System.out.println("....");
            }
        }
    }

    public static void main(String[] args) {
        final RecordExample example = new RecordExample();

        while (true) {
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
}