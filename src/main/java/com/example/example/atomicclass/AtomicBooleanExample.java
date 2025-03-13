package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {
    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        // 设置值
        atomicBoolean.set(true);
        System.out.println("Set value: " + atomicBoolean.get()); // true

        // 比较并交换（CAS）
        boolean casSuccess = atomicBoolean.compareAndSet(true, false);
        System.out.println("CAS success: " + casSuccess + ", new value: " + atomicBoolean.get()); // true, false
    }
}