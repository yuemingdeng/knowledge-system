package com.example.utils.ratelimitutils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 限流-漏桶算法
 */
public class LeakyBucketRateLimiter {
    private final long capacity; // 漏桶容量
    private final long rate; // 漏桶流出速率（请求/毫秒）
    private final AtomicLong waterLevel; // 当前水位
    private long lastLeakTime; // 上次漏水时间

    public LeakyBucketRateLimiter(long capacity, long rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.waterLevel = new AtomicLong(0);
        this.lastLeakTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        // 计算漏水量
        long leaked = (currentTime - lastLeakTime) * rate;
        if (leaked > 0) {
            waterLevel.set(Math.max(0, waterLevel.get() - leaked));
            lastLeakTime = currentTime;
        }
        // 检查当前水位是否超过容量
        if (waterLevel.get() < capacity) {
            waterLevel.incrementAndGet();
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyBucketRateLimiter limiter = new LeakyBucketRateLimiter(10, 1); // 容量 10，每秒流出 1 个请求
        for (int i = 0; i < 20; i++) {
            System.out.println("Request " + i + ": " + (limiter.tryAcquire() ? "Allowed" : "Denied"));
            Thread.sleep(100); // 模拟请求间隔
        }
    }
}
