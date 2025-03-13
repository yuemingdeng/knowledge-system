package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayExample {
    public static void main(String[] args) {
        int[] array = {1, 2, 3};
        AtomicIntegerArray atomicArray = new AtomicIntegerArray(array);

        // 获取值
        System.out.println("Value at index 1: " + atomicArray.get(1)); // 2

        // 设置值
        atomicArray.set(1, 5);
        System.out.println("New value at index 1: " + atomicArray.get(1)); // 5

        // 原子递增
        int incrementedValue = atomicArray.incrementAndGet(1);
        System.out.println("Incremented value at index 1: " + incrementedValue); // 6

        // 比较并交换（CAS）
        boolean casSuccess = atomicArray.compareAndSet(1, 6, 10);
        System.out.println("CAS success: " + casSuccess + ", new value at index 1: " + atomicArray.get(1)); // true, 10
    }
}
