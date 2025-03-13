package com.example.example.threadlocal;

/**
 * 可以通过重写 initialValue() 方法为 ThreadLocal 设置初始值。
 */
public class ThreadLocalInitialValueExample {
    // 创建一个 ThreadLocal 变量并设置初始值
    private static final ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Default Value");

    public static void main(String[] args) {
        // 主线程的 ThreadLocal 值
        System.out.println("Main Thread: " + threadLocal.get()); // Default Value

        // 创建新线程
        Thread thread = new Thread(() -> {
            System.out.println("Thread-1: " + threadLocal.get()); // Default Value
            threadLocal.set("Thread-1 Value");
            System.out.println("Thread-1 after set: " + threadLocal.get()); // Thread-1 Value
        });

        // 启动线程
        thread.start();

        // 清理 ThreadLocal 变量
        threadLocal.remove();
        System.out.println("Main Thread after remove: " + threadLocal.get()); // Default Value
    }
}