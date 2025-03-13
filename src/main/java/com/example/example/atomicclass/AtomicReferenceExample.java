package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {
    public static void main(String[] args) {
        AtomicReference<String> atomicRef = new AtomicReference<>("Hello");

        // 设置值
        atomicRef.set("World");
        System.out.println("Set value: " + atomicRef.get()); // World

        // 比较并交换（CAS）
        boolean casSuccess = atomicRef.compareAndSet("World", "Java");
        System.out.println("CAS success: " + casSuccess + ", new value: " + atomicRef.get()); // true, Java
    }
}
