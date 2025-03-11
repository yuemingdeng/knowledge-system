package com.example.utils.ratelimitutils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 令牌桶算法
 */
public class TokenBucketRateLimiter {
    private final long capacity; // 令牌桶容量
    private final long rate; // 令牌添加速率（令牌/毫秒）
    private final AtomicLong tokens; // 当前令牌数
    private long lastRefillTime; // 上次添加令牌时间

    public TokenBucketRateLimiter(long capacity, long rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = new AtomicLong(capacity);
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        // 计算新增的令牌数
        long refilled = (currentTime - lastRefillTime) * rate;
        if (refilled > 0) {
            tokens.set(Math.min(capacity, tokens.get() + refilled));
            lastRefillTime = currentTime;
        }
        // 检查是否有足够的令牌
        if (tokens.get() > 0) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(10, 1); // 容量 10，每秒添加 1 个令牌
        for (int i = 0; i < 20; i++) {
            System.out.println("Request " + i + ": " + (limiter.tryAcquire() ? "Allowed" : "Denied"));
            Thread.sleep(100); // 模拟请求间隔
        }
    }
}
