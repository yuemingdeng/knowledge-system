package com.example.utils.threadpool;

/**
 * 监控线程池状态
 */
import java.util.concurrent.TimeUnit;

public class ThreadPoolMonitor implements Runnable {
    private final CustomThreadPool threadPool;

    public ThreadPoolMonitor(CustomThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            CustomThreadPool.ThreadPoolStatus status = threadPool.getStatus();
            System.out.println("ThreadPool Status: " + status);
            try {
                TimeUnit.SECONDS.sleep(5); // 每隔 5 秒监控一次
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}