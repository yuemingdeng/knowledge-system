package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicReferenceArrayExample {
    public static void main(String[] args) {
        String[] array = {"A", "B", "C"};
        AtomicReferenceArray<String> atomicArray = new AtomicReferenceArray<>(array);

        // 获取值
        System.out.println("Value at index 1: " + atomicArray.get(1)); // B

        // 设置值
        atomicArray.set(1, "D");
        System.out.println("New value at index 1: " + atomicArray.get(1)); // D

        // 比较并交换（CAS）
        boolean casSuccess = atomicArray.compareAndSet(1, "D", "E");
        System.out.println("CAS success: " + casSuccess + ", new value at index 1: " + atomicArray.get(1)); // true, E
    }
}
