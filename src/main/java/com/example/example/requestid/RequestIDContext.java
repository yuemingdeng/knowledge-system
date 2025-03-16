package com.example.example.requestid;

import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class RequestIDContext {
    // 使用 ThreadLocal 存储 requestID
    private static final ThreadLocal<String> requestIDHolder = new ThreadLocal<>();

    // 使用 InheritableThreadLocal 支持跨线程传递
    private static final InheritableThreadLocal<Map<String, String>> inheritableContext = new InheritableThreadLocal<>();

    /**
     * 生成并设置 requestID
     */
    public static void setRequestID() {
        String requestID = UUID.randomUUID().toString();
        requestIDHolder.set(requestID);
        MDC.put("requestID", requestID); // 将 requestID 添加到 MDC
    }

    /**
     * 获取当前 requestID
     */
    public static String getRequestID() {
        return requestIDHolder.get();
    }

    /**
     * 清理 requestID
     */
    public static void clearRequestID() {
        requestIDHolder.remove();
        MDC.remove("requestID"); // 从 MDC 中移除 requestID
    }

    /**
     * 包装 Runnable，支持跨线程传递 requestID
     */
    public static Runnable wrapRunnable(Runnable runnable) {
        Map<String, String> context = MDC.getCopyOfContextMap(); // 获取当前 MDC 上下文
        return () -> {
            if (context != null) {
                MDC.setContextMap(context); // 在子线程中设置 MDC 上下文
            }
            try {
                runnable.run();
            } finally {
                MDC.clear(); // 清理子线程的 MDC 上下文
            }
        };
    }

    /**
     * 包装 Callable，支持跨线程传递 requestID
     */
    public static <T> Callable<T> wrapCallable(Callable<T> callable) {
        Map<String, String> context = MDC.getCopyOfContextMap(); // 获取当前 MDC 上下文
        return () -> {
            if (context != null) {
                MDC.setContextMap(context); // 在子线程中设置 MDC 上下文
            }
            try {
                return callable.call();
            } finally {
                MDC.clear(); // 清理子线程的 MDC 上下文
            }
        };
    }
}
