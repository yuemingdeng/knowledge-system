package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongExample {
    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong(100);

        // 设置值
        atomicLong.set(200);
        System.out.println("Set value: " + atomicLong.get()); // 200

        // 原子递增
        long incrementedValue = atomicLong.incrementAndGet();
        System.out.println("Incremented value: " + incrementedValue); // 201

        // 原子递减
        long decrementedValue = atomicLong.decrementAndGet();
        System.out.println("Decremented value: " + decrementedValue); // 200

        // 原子加法
        long addedValue = atomicLong.addAndGet(50);
        System.out.println("Added value: " + addedValue); // 250

        // 比较并交换（CAS）
        boolean casSuccess = atomicLong.compareAndSet(250, 300);
        System.out.println("CAS success: " + casSuccess + ", new value: " + atomicLong.get()); // true, 300
    }
}
