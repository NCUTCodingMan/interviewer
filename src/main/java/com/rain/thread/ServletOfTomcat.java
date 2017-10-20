package com.rain.thread;

/**
 * Servlet为什么是单实例多线程模型
 * 1.web容器启动或者用户第一次访问Servlet时, 创建Servlet单实例, 仅仅初始化一次
 * 2.web容器在处理请求时会从其维护的线程中
 * @author Administrator
 *
 */
public class ServletOfTomcat {
    
    public static void main(String[] args) {
        Servlet servlet = Servlet.getInstance();
        // 模拟客户端发出的多个请求
        for (int i = 0;i < 10;i++) {
            new Thread(new Job(servlet)).start();
        }
    }
    
    /**
     * 单例模式,注意volatile与synchronized
     * @author Administrator
     */
    private static class Servlet {
        private static volatile Servlet servlet;
        private Servlet() {}
        public static synchronized Servlet getInstance() {
            if (servlet == null) {
                servlet = new Servlet();
            }
            return servlet;
        }
        public void service() {System.out.println("do something");}
    }
    
    /**
     * 多个线程指向相同的引用
     * @author Administrator
     */
    private static class Job implements Runnable {
        private Servlet servlet;
        public Job(Servlet servlet) {
            this.servlet = servlet;
        }
        @Override
        public void run() {
            servlet.service();
        }
    } 
}