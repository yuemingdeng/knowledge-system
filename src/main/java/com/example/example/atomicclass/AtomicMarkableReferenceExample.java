package com.example.example.atomicclass;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 用于通过布尔标记来标记引用
 */
public class AtomicMarkableReferenceExample {
    public static void main(String[] args) {
        String initialRef = "A";
        boolean initialMark = false;
        AtomicMarkableReference<String> atomicMarkableRef = new AtomicMarkableReference<>(initialRef, initialMark);

        // 获取值和标记
        boolean[] markHolder = new boolean[1];
        String currentRef = atomicMarkableRef.get(markHolder);
        System.out.println("Current value: " + currentRef + ", mark: " + markHolder[0]); // A, false

        // 设置新值和标记
        boolean setSuccess = atomicMarkableRef.compareAndSet(currentRef, "B", markHolder[0], true);
        System.out.println("Set success: " + setSuccess + ", new value: " + atomicMarkableRef.getReference() + ", new mark: " + atomicMarkableRef.isMarked()); // true, B, true
    }
}
