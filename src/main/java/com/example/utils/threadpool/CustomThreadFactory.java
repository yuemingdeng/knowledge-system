package com.example.utils.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 */
public class CustomThreadFactory implements ThreadFactory {
    private final AtomicInteger threadCount = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("CustomThread-" + threadCount.incrementAndGet());
        return thread;
    }
}
