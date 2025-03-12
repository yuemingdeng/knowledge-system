package com.example.utils.redis.bloomfilter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

public class BloomFilter {

    private static final String REDIS_KEY = "bloom_filter";
    private static final int BIT_SIZE = 2 << 28; // 位图大小，约为2^29
    private static final int HASH_COUNT = 5;    // 哈希函数个数

    private Jedis jedis;

    public BloomFilter(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 添加单个元素到布隆过滤器
     */
    public void add(String item) {
        addAll(Collections.singletonList(item));
    }

    /**
     * 批量添加元素到布隆过滤器
     */
    public void addAll(List<String> items) {
        try (Pipeline pipeline = jedis.pipelined()) {
            for (String item : items) {
                int[] hashIndexes = getHashIndexes(item);
                for (int index : hashIndexes) {
                    pipeline.setbit(REDIS_KEY, index, true);
                }
            }
            pipeline.sync();
        }
    }

    /**
     * 检查元素是否可能存在于布隆过滤器中
     */
    public boolean contains(String item) {
        int[] hashIndexes = getHashIndexes(item);
        try (Pipeline pipeline = jedis.pipelined()) {
            for (int index : hashIndexes) {
                pipeline.getbit(REDIS_KEY, index);
            }
            List<Object> results = pipeline.syncAndReturnAll();
            for (Object result : results) {
                if (!(Boolean) result) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除布隆过滤器（清空位图）
     */
    public void delete() {
        jedis.del(REDIS_KEY);
    }

    /**
     * 计算元素的多个哈希值并映射到位图中的索引
     */
    private int[] getHashIndexes(String item) {
        int[] indexes = new int[HASH_COUNT];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(item.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < HASH_COUNT; i++) {
                // 使用不同的哈希函数（通过改变种子）
                int hash = hash(digest, i);
                indexes[i] = Math.abs(hash % BIT_SIZE);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
        return indexes;
    }

    /**
     * 哈希函数
     */
    private int hash(byte[] digest, int seed) {
        int hash = 0;
        for (int i = 0; i < 4; i++) {
            hash = (hash << 8) | (digest[(seed + i) % digest.length] & 0xFF);
        }
        return hash;
    }

    /**
     * 后自己加，为了能在外部关闭jedis
     * @return
     */
    public Jedis getJedis() {
        return jedis;
    }
}