package com.rain.thread;

/**
 * Servlet单实例多线程模型原理
 * 1.web容器启动或者用户第一次访问Servlet时,创建Servlet单实例,仅仅初始化一次
 * 2.web容器在处理请求时会从其维护的线程池中调度等待执行的线程给请求者
 * 3.线程执行Servlet的service()
 * 4.请求结束,线程放回线程池,等待被调用
 *
 * Servlet单实例多线程模型优势
 * 1.单例模式,减少了Servlet的内存开销
 * 2.多线程处理用户请求,响应迅速
 * 3.web容器并不会判断当前的多个请求是同一个Servlet处理还是多个Servlet处理,为请求分配一个线程
 *   执行其service();若多个请求相同,service()将会在并发情况下执行
 *
 * Servlet单实例多线程模型如何保证并发安全
 * 1.实现SingleThreadModel (实现了该接口,每次请求都会创建一个Servlet对象,问题显而易见内存消耗)
 * 2.通过synchronized对实例变量减伤同步块,此时Servlet性能会收到特别大的影响
 * 3.尽量使用实例变量,而使用局部变量(局部变量是分配在堆栈中的,每个线程私有的,不会有线程安全问题)
 *
 * 这里引出了几个问题
 * 1.线程池的概念
 * 2.调度线程与工作者线程
 * 3.tomcat设置connector线程池中的数目
 * 4.struts2的action每次请求都会产生一个实例,因此是线程安全的
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