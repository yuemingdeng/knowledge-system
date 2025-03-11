package com.example.utils.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁-带续期功能
 * 单点-没考虑红锁
 */
public class RedisDistributedLockWithRenewal {
    private static final String LOCK_KEY = "distributed_lock";
    private static final int LOCK_EXPIRE_TIME = 30000; // 锁的超时时间（毫秒）
    private static final int WATCHDOG_INTERVAL = 10000; // 锁续期间隔（毫秒）

    private Jedis jedis;
    private String requestId;
    private ScheduledExecutorService watchdog;

    public RedisDistributedLockWithRenewal(Jedis jedis) {
        this.jedis = jedis;
        this.watchdog = Executors.newScheduledThreadPool(1);
    }

    /**
     * 尝试获取锁
     *
     * @param requestId 请求标识，用于解锁时验证
     * @return 是否获取成功
     */
    public boolean tryLock(String requestId) {
        this.requestId = requestId;
        SetParams params = SetParams.setParams().nx().px(LOCK_EXPIRE_TIME);
        String result = jedis.set(LOCK_KEY, requestId, params);
        if ("OK".equals(result)) {
            // 启动锁续期线程
            startWatchdog();
            return true;
        }
        return false;
    }

    /**
     * 启动锁续期线程
     */
    private void startWatchdog() {
        watchdog.scheduleAtFixedRate(() -> {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('pexpire', KEYS[1], ARGV[2]) " +
                    "else " +
                    "return 0 " +
                    "end";
            jedis.eval(script, 1, LOCK_KEY, requestId, String.valueOf(LOCK_EXPIRE_TIME));
        }, WATCHDOG_INTERVAL, WATCHDOG_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     * 释放锁
     *
     * @return 是否释放成功
     */
    public boolean unlock() {
        // 停止锁续期线程
        watchdog.shutdown();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        Object result = jedis.eval(script, 1, LOCK_KEY, requestId);
        return Long.valueOf(1).equals(result);
    }

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("localhost", 6379);
        RedisDistributedLockWithRenewal lock = new RedisDistributedLockWithRenewal(jedis);

        String requestId = "request_123";
        if (lock.tryLock(requestId)) {
            try {
                System.out.println("获取锁成功，执行业务逻辑...");
                Thread.sleep(40000); // 模拟长时间任务
            } finally {
                if (lock.unlock()) {
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