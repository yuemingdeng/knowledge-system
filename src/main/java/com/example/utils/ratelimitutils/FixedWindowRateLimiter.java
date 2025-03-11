package com.example.utils.ratelimitutils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流-固定窗口
 */
public class FixedWindowRateLimiter {
    private final int limit; // 窗口内允许的最大请求数
    private final long windowSizeInMillis; // 窗口大小（毫秒）
    private final AtomicInteger counter; // 当前窗口的请求计数
    private long windowStart; // 当前窗口的开始时间

    public FixedWindowRateLimiter(int limit, long windowSizeInMillis) {
        this.limit = limit;
        this.windowSizeInMillis = windowSizeInMillis;
        this.counter = new AtomicInteger(0);
        this.windowStart = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        // 如果当前时间已经超过窗口，重置窗口
        if (currentTime - windowStart > windowSizeInMillis) {
            counter.set(0);
            windowStart = currentTime;
        }
        // 检查当前窗口的请求数是否超过限制
        return counter.incrementAndGet() <= limit;
    }

    public static void main(String[] args) throws InterruptedException {
        FixedWindowRateLimiter limiter = new FixedWindowRateLimiter(10, 1000); // 每秒最多 10 个请求
        for (int i = 0; i < 20; i++) {
            System.out.println("Request " + i + ": " + (limiter.tryAcquire() ? "Allowed" : "Denied"));
            Thread.sleep(100); // 模拟请求间隔
        }
    }
}
