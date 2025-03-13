package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 用于解决 ABA 问题，通过版本号（stamp）来标记引用。
 */
public class AtomicStampedReferenceExample {
    public static void main(String[] args) {
        String initialRef = "A";
        int initialStamp = 0;
        AtomicStampedReference<String> atomicStampedRef = new AtomicStampedReference<>(initialRef, initialStamp);

        // 获取值和版本号
        int[] stampHolder = new int[1];
        String currentRef = atomicStampedRef.get(stampHolder);
        System.out.println("Current value: " + currentRef + ", stamp: " + stampHolder[0]); // A, 0

        // 设置新值和版本号
        boolean setSuccess = atomicStampedRef.compareAndSet(currentRef, "B", stampHolder[0], stampHolder[0] + 1);
        System.out.println("Set success: " + setSuccess + ", new value: " + atomicStampedRef.getReference() + ", new stamp: " + atomicStampedRef.getStamp()); // true, B, 1
    }
}
