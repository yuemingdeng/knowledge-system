package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    public static void main(String[] args) {
        AtomicInteger atomicInt = new AtomicInteger(0);

        // 设置值
        atomicInt.set(10);
        System.out.println("Set value: " + atomicInt.get()); // 10

        // 原子递增
        int incrementedValue = atomicInt.incrementAndGet();
        System.out.println("Incremented value: " + incrementedValue); // 11

        // 原子递减
        int decrementedValue = atomicInt.decrementAndGet();
        System.out.println("Decremented value: " + decrementedValue); // 10

        // 原子加法
        int addedValue = atomicInt.addAndGet(5);
        System.out.println("Added value: " + addedValue); // 15

        // 比较并交换（CAS）
        boolean casSuccess = atomicInt.compareAndSet(15, 20);
        System.out.println("CAS success: " + casSuccess + ", new value: " + atomicInt.get()); // true, 20
    }
}
