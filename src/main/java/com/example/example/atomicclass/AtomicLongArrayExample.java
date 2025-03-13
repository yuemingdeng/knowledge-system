package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicLongArrayExample {
    public static void main(String[] args) {
        long[] array = {100L, 200L, 300L};
        AtomicLongArray atomicArray = new AtomicLongArray(array);

        // 获取值
        System.out.println("Value at index 1: " + atomicArray.get(1)); // 200

        // 设置值
        atomicArray.set(1, 500L);
        System.out.println("New value at index 1: " + atomicArray.get(1)); // 500

        // 原子递增
        long incrementedValue = atomicArray.incrementAndGet(1);
        System.out.println("Incremented value at index 1: " + incrementedValue); // 501

        // 比较并交换（CAS）
        boolean casSuccess = atomicArray.compareAndSet(1, 501L, 1000L);
        System.out.println("CAS success: " + casSuccess + ", new value at index 1: " + atomicArray.get(1)); // true, 1000
    }
}
