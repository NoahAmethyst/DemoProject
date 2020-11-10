package com.boot_demo.demo1.aop;

import com.boot_demo.demo1.anotations.RedisLock;
import com.boot_demo.demo1.utils.RedisLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LockMethodAspect {
    @Resource
    private RedisLockHelper redisLockHelper;

    @Resource
    private JedisPool jedisPool;
    

    @Around("@annotation(com.boot_demo.demo1.anotations.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(0);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String value = UUID.randomUUID().toString();
        String key = redisLock.key();
        try {
            final boolean isLock = redisLockHelper.lock(jedis,key, value, redisLock.expire(), redisLock.timeUnit());
            log.info("isLock : {}",isLock);
            if (!isLock) {
                log.error("获取锁失败");
                throw new RuntimeException("获取锁失败");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("系统异常");
            }
        }  finally {
            log.info("释放锁");
            redisLockHelper.unlock(jedis,key, value);
            jedis.close();
        }

    }
}


