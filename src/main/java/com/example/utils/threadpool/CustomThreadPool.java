package com.example.utils.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 自定义线程池-后续可以优化下
 */
public class CustomThreadPool {

    private final ThreadPoolExecutor executor;
    private final AtomicLong completedTaskCount = new AtomicLong(0);

    public CustomThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                            int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);
        this.executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );
    }

    /**
     * 提交任务
     */
    public void execute(Runnable task) {
        executor.execute(() -> {
            try {
                task.run();
            } finally {
                completedTaskCount.incrementAndGet();
            }
        });
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * 获取线程池状态
     */
    public ThreadPoolStatus getStatus() {
        return new ThreadPoolStatus(
                executor.getPoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                completedTaskCount.get()
        );
    }

    /**
     * 线程池状态类
     */
    public static class ThreadPoolStatus {
        private final int poolSize; // 当前线程数
        private final int activeCount; // 活动线程数
        private final int queueSize; // 任务队列大小
        private final long completedTaskCount; // 完成任务数

        public ThreadPoolStatus(int poolSize, int activeCount, int queueSize, long completedTaskCount) {
            this.poolSize = poolSize;
            this.activeCount = activeCount;
            this.queueSize = queueSize;
            this.completedTaskCount = completedTaskCount;
        }

        @Override
        public String toString() {
            return String.format(
                    "ThreadPoolStatus{poolSize=%d, activeCount=%d, queueSize=%d, completedTaskCount=%d}",
                    poolSize, activeCount, queueSize, completedTaskCount
            );
        }
    }
}
