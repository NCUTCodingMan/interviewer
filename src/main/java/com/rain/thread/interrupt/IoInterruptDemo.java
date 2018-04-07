package com.rain.thread.interrupt;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author rain
 * created on 2018/4/7
 */
public class IoInterruptDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                });

        ServerSocket server = new ServerSocket(8080);
        InputStream inputStream = new Socket("localhost", 8080).getInputStream();
        executorService.execute(new IOBlock(inputStream));
        executorService.execute(new IOBlock(System.in));

        TimeUnit.MILLISECONDS.sleep(100);
        executorService.shutdownNow();

        System.out.println("Closing: " + inputStream.getClass().getName());
        // 流关闭之后, 马上就检测到中断信息了, 接着输出了异常(关闭了流, 因此读取流失败了)中的相关代码
        inputStream.close();

        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Closing: " + System.in.getClass().getName());
        System.in.close();

        server.close();
    }
}