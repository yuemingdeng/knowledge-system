package com.example.example.requestid;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class MultiThreadExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // 设置 requestID
        RequestIDContext.setRequestID();
        System.out.println("Main Thread - RequestID: " + RequestIDContext.getRequestID());

        // 提交任务
        executor.submit(RequestIDContext.wrapRunnable(() -> {
            System.out.println("Child Thread - RequestID: " + RequestIDContext.getRequestID());
        }));

        // 清理 requestID
        RequestIDContext.clearRequestID();
        executor.shutdown();
    }

    public String test()  {
        AtomicReference<String> result = new AtomicReference<>("");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // 设置 requestID
        RequestIDContext.setRequestID();
        result.set(result + "Main Thread - RequestID: " + RequestIDContext.getRequestID());

        // 提交任务
        Future<String> future1 = executor.submit(new CallableTask("Task-1", RequestIDContext.getRequestID()));
        Future<String> future2 = executor.submit(new CallableTask("Task-2", RequestIDContext.getRequestID()));


        String value = null;
        try {
            value = result.get() + "---------" +  future1.get() + "---------" +  future2.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        // 清理 requestID
        RequestIDContext.clearRequestID();
        executor.shutdown();
        return value;
    }

    // 定义一个 Callable 任务
    static class CallableTask implements Callable<String> {
        private final String name;
        private String value;

        public CallableTask(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String call() throws Exception {
            return value; // 返回计算结果
        }
    }
}