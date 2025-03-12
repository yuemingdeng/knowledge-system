package com.example.utils.redis.bloomfilter;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

public class BloomFilterSingleton {

    private static BloomFilter instance;
    private static Jedis jedis;

    private BloomFilterSingleton() {}

    public static synchronized BloomFilter getInstance() {
        if (instance == null) {
            jedis = new Jedis("localhost", 6379);
            instance = new BloomFilter(jedis);
        }
        return instance;
    }

    public static void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
