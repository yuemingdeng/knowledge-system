package com.example.utils.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池测试代码
 */
public class CustomThreadPoolExample {

    public static void main(String[] args) {
        // 自定义线程池参数
        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 60;
        TimeUnit unit = TimeUnit.SECONDS;
        int queueCapacity = 10;
        ThreadFactory threadFactory = new CustomThreadFactory();
        RejectedExecutionHandler handler = new CustomRejectedExecutionHandler();

        // 创建自定义线程池
        CustomThreadPool threadPool = new CustomThreadPool(
                corePoolSize, maximumPoolSize, keepAliveTime, unit,
                queueCapacity, threadFactory, handler
        );

        // 启动监控线程
        Thread monitorThread = new Thread(new ThreadPoolMonitor(threadPool));
        monitorThread.setDaemon(true); // 设置为守护线程
        monitorThread.start();

        // 提交任务
        for (int i = 0; i < 15; i++) {
            int taskId = i;
            threadPool.execute(() -> {
                System.out.println("Task " + taskId + " is running on thread: " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(3); // 模拟任务执行
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed.");
            });
        }

        // 关闭线程池
        threadPool.shutdown();
    }
}
