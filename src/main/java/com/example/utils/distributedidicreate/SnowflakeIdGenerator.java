package com.example.utils.distributedidicreate;

/**
 * 雪花算法-包含时间回拨处理
 */
public class SnowflakeIdGenerator {
    private final long epoch = 1609459200000L; // 2021-01-01 00:00:00
    private final long machineIdBits = 10L; // 机器 ID 位数
    private final long sequenceBits = 12L; // 序列号位数

    private final long maxMachineId = ~(-1L << machineIdBits); // 最大机器 ID
    private final long maxSequence = ~(-1L << sequenceBits); // 最大序列号

    private final long machineIdShift = sequenceBits; // 机器 ID 左移位数
    private final long timestampShift = sequenceBits + machineIdBits; // 时间戳左移位数

    private long machineId; // 机器 ID
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L; // 上次生成 ID 的时间戳

    public SnowflakeIdGenerator(long machineId) {
        if (machineId < 0 || machineId > maxMachineId) {
            throw new IllegalArgumentException("Machine ID must be between 0 and " + maxMachineId);
        }
        this.machineId = machineId;
    }

    /**
     * 生成下一个 ID
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 处理时钟回拨
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards! Refusing to generate ID.");
        }

        // 如果是同一毫秒内生成的，则递增序列号
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            // 如果序列号溢出，则等待下一毫秒
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 如果是新的毫秒，则重置序列号
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 生成 ID
        return ((timestamp - epoch) << timestampShift)
                | (machineId << machineIdShift)
                | sequence;
    }

    /**
     * 等待下一毫秒
     */
    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1); // 机器 ID 为 1
        for (int i = 0; i < 10; i++) {
            System.out.println(generator.nextId());
        }
    }
}
