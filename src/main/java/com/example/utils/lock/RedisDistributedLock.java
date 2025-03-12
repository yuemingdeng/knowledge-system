package com.example.utils.lock;

/**
 * 使用redis实现分布式锁
 * 使用 Redis 的 SETNX 命令（set if not exists）尝试获取锁。
 * 使用 EXPIRE 命令设置锁的超时时间，避免死锁。
 * 使用 Lua 脚本保证解锁的原子性。
 * create at 20250310
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

@Component
public class RedisDistributedLock {
    private static final String LOCK_KEY = "distributed_lock";
    private static final String LOCK_VALUE = "locked";
    private static final int LOCK_EXPIRE_TIME = 30000; // 锁的超时时间（毫秒）

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    private Jedis jedis;

    public RedisDistributedLock(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 尝试获取锁
     *
     * @param requestId 请求标识，用于解锁时验证
     * @return 是否获取成功
     */
    public boolean tryLock(String requestId) {
        SetParams params = SetParams.setParams().nx().px(LOCK_EXPIRE_TIME);
        String result = jedis.set(LOCK_KEY, requestId, params);
        return "OK".equals(result);
    }

    /**
     * 释放锁
     *
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean unlock(String requestId) {
        //Lua脚本保证释放锁的原子性
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        Object result = jedis.eval(script, 1, LOCK_KEY, requestId);
        return Long.valueOf(1).equals(result);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        RedisDistributedLock lock = new RedisDistributedLock(jedis);

        String requestId = "request_123";
        if (lock.tryLock(requestId)) {
            try {
                System.out.println("获取锁成功，执行业务逻辑...");
                Thread.sleep(1000); // 模拟业务逻辑
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.unlock(requestId)) {
                    System.out.println("释放锁成功");
                } else {
                    System.out.println("释放锁失败");
                }
            }
        } else {
            System.out.println("获取锁失败");
        }
    }
}
