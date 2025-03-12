package com.example.utils.distributedidicreate;

import redis.clients.jedis.Jedis;

public class RedisIncrementIdGenerator {
    private static final String REDIS_KEY = "increment_id"; // Redis 中存储 ID 的键
    private Jedis jedis;

    public RedisIncrementIdGenerator(String host, int port) {
        this.jedis = new Jedis(host, port);
    }

    /**
     * 生成自增 ID
     */
    public long generateId() {
        // 使用 INCR 命令原子性地递增 ID
        return jedis.incr(REDIS_KEY);
    }

    /**
     * 重置 ID
     */
    public void resetId() {
        jedis.set(REDIS_KEY, "0");
    }

    /**
     * 优化-支持多个业务键
     * @param businessKey
     * @return
     */
    public long generateId(String businessKey) {
        return jedis.incr(REDIS_KEY + ":" + businessKey);
    }

    /**
     * 优化-设置初始值
     * @param initialValue
     */
    public void setInitialValue(long initialValue) {
        jedis.set(REDIS_KEY, String.valueOf(initialValue));
    }

    public static void main(String[] args) {
        // 创建 Redis 自增 ID 生成器
        RedisIncrementIdGenerator idGenerator = new RedisIncrementIdGenerator("localhost", 6379);

        // 生成 10 个自增 ID
        for (int i = 0; i < 10; i++) {
            long id = idGenerator.generateId();
            System.out.println("Generated ID: " + id);
        }

        // 重置 ID
        idGenerator.resetId();
        System.out.println("ID reset to 0");
    }
}
