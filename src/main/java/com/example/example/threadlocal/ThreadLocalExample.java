package com.example.example.threadlocal;

/**
 * ThreadLocal基本使用
 */
public class ThreadLocalExample {
    // 创建一个 ThreadLocal 变量
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        // 创建两个线程
        Thread thread1 = new Thread(() -> {
            // 设置线程1的 ThreadLocal 值
            threadLocal.set("Thread-1 Value");
            System.out.println("Thread-1: " + threadLocal.get());
        });

        Thread thread2 = new Thread(() -> {
            // 设置线程2的 ThreadLocal 值
            threadLocal.set("Thread-2 Value");
            System.out.println("Thread-2: " + threadLocal.get());
        });

        // 启动线程
        thread1.start();
        thread2.start();

        // 主线程的 ThreadLocal 值
        threadLocal.set("Main Thread Value");
        System.out.println("Main Thread: " + threadLocal.get());

        // 清理 ThreadLocal 变量
        threadLocal.remove();
        System.out.println("Main Thread after remove: " + threadLocal.get()); // null
    }
}
