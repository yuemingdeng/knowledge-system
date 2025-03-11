package com.example.utils.ratelimitutils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 限流-滑动窗口
 */
public class SlidingWindowRateLimiter {
    private final int limit; // 窗口内允许的最大请求数
    private final long windowSizeInMillis; // 窗口大小（毫秒）
    private final Queue<Long> timestamps; // 存储请求的时间戳

    public SlidingWindowRateLimiter(int limit, long windowSizeInMillis) {
        this.limit = limit;
        this.windowSizeInMillis = windowSizeInMillis;
        this.timestamps = new LinkedList<>();
    }

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        // 移除窗口外的旧时间戳
        while (!timestamps.isEmpty() && currentTime - timestamps.peek() > windowSizeInMillis) {
            timestamps.poll();
        }
        // 检查当前窗口的请求数是否超过限制
        if (timestamps.size() < limit) {
            timestamps.offer(currentTime);
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(10, 1000); // 每秒最多 10 个请求
        for (int i = 0; i < 20; i++) {
            System.out.println("Request " + i + ": " + (limiter.tryAcquire() ? "Allowed" : "Denied"));
            Thread.sleep(100); // 模拟请求间隔
        }
    }
}