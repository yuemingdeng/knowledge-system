package com.example.example.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  线程池中使用ThreadLocal
 */
public class ThreadLocalWithThreadPoolExample {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 提交任务
        for (int i = 0; i < 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                // 设置 ThreadLocal 值
                threadLocal.set("Task-" + taskId + " Value");
                System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());

                // 清理 ThreadLocal 变量
                threadLocal.remove();
            });
        }

        // 关闭线程池
        executor.shutdown();
    }
}
