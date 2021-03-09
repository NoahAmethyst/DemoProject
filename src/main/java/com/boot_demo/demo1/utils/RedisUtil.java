package com.boot_demo.demo1.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
@Slf4j
public final class RedisUtil {

    @Resource
    private RedisTemplate<String, JSONObject> redisTemplate;

    @Resource
    private RedisTemplate<String, String> redisStringTemplate;


    // =============================common============================

    /**
     * get all keys by key pattern
     */

    public Set<String> keys(String keyPattern) {
        return redisStringTemplate.keys(keyPattern);
    }

    /**
     * set key expire time
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get expire time by key
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * judge whether key is exist
     */
    public boolean hasKey(String key) {
        try {

            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("judge key is exist error:{}", e.getMessage());
            return false;
        }
    }

    /**
     * delete key
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    // ============================String=============================

    /**
     * get specific type value
     */
    public T get(String key, Class<T> resType) {
        if (key == null) {
            throw new RuntimeException("redis key must not be null.");
        }
        return redisTemplate.opsForValue().get(key).toJavaObject(resType);
    }

    /**
     * get string value
     */
    public String getString(String key) {
        if (key == null) {
            throw new RuntimeException("redis key must no be null.");
        }
        return redisStringTemplate.opsForValue().get(key);
    }


    /**
     * set json value
     */
    public boolean set(String key, JSONObject value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set key:{} json value:{} error,", key, value.toJSONString(), e.getMessage());
            return false;
        }
    }

    /**
     * set string value
     */
    public boolean setString(String key, String value) {
        try {
            redisStringTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("set key:{} string value:{} error,", key, value, e.getMessage());
            return false;
        }
    }


    /**
     * set json value and set expire time
     */
    public boolean set(String key, JSONObject value, int seconds) {
        try {
            if (seconds > 0) {
                redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set key:{} json value:{} expireTime:{} error,", key, value, seconds, e.getMessage());
            return false;
        }
    }

    /**
     * set string value and set expire time
     */
    public boolean setString(String key, String value, int seconds) {
        try {
            if (seconds > 0) {
                redisStringTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            } else {
                setString(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("set key:{} string value:{} expireTime:{} error,", key, value, seconds, e.getMessage());
            return false;
        }
    }

    /**
     * increasing or decreasing
     */
    public int incr(String key, int delta) {
        return Math.toIntExact(redisTemplate.opsForValue().increment(key, delta));
    }


    // ============================list=============================

    /**
     * get list json value by key
     *
     * @param key 键
     * @return
     */
    public List<T> lGet(String key, Class<T> resType) {
        List<T> values = new ArrayList<>();
        try {
            List<JSONObject> jsonValues = redisTemplate.opsForList().range(key, 0, -1);
            values = new ArrayList<>();
            List<T> finalValues = values;
            jsonValues.forEach(jsonValue -> {
                finalValues.add(jsonValue.toJavaObject(resType));
            });
            values = finalValues;
            return values;
        } catch (Exception e) {
            log.error("get list by key:{}  error:{}", key, e.getMessage());
            return values;
        }
    }

    /**
     * get list json value by key
     *
     * @return
     */
    public List<String> lGetString(String key) {
        List<String> values = new ArrayList<>();
        try {
            values = redisStringTemplate.opsForList().range(key, 0, -1);
            return values;
        } catch (Exception e) {
            log.error("get list by key:{} error:{}", key);
            return values;
        }
    }

    /**
     * add json value to list
     */
    public void lAdd(String key, JSONObject value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("add value :{} to list key:{} error:{}", value.toJSONString(), key, e.getMessage());
        }
    }

    /**
     * add string value to list
     */
    public void lAdd(String key, String value) {
        try {
            redisStringTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.error("add value :{} to list key:{} error:{}", value, key, e.getMessage());
        }
    }


    /**
     * add json values to list
     */
    public void lAddList(String key, List<JSONObject> values) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("add values size:{} to list key:{} error:{}", key, e.getMessage());
        }
    }


    /**
     * add string values to list
     */
    public void lAddStringList(String key, List<String> values) {
        try {
            redisStringTemplate.opsForList().rightPushAll(key, values);
        } catch (Exception e) {
            log.error("add values  to list key:{} error:{}", key, e.getMessage());
        }
    }

    /**
     * add json values to limited list
     * the size is fixed
     * first in first out
     */
    public void lAddLimitedList(String key, List<JSONObject> values, int size) {
        try {
            int currentSize = Math.toIntExact(redisTemplate.opsForList().size(key));
            if (currentSize >= size) {
                int overNumber = currentSize - size + 1;
                redisTemplate.opsForList().trim(key, overNumber, -1);
            }
            this.lAddList(key, values);


        } catch (Exception e) {
            log.error("add json values to limited list key:{} error:{}", key, e.getMessage());
        }
    }

    /**
     * add string values to limited list
     * the size is fixed
     * first in first out
     */
    public void lAddLimitedListString(String key, List<String> values, int size) {
        try {
            int currentSize = Math.toIntExact(redisStringTemplate.opsForList().size(key));
            if (currentSize >= size) {
                int overNumber = currentSize - size + 1;
                redisStringTemplate.opsForList().trim(key, overNumber, -1);
            }
            this.lAddStringList(key, values);
        } catch (Exception e) {
            log.error("add string values to limited list key:{} error:{}", key, e.getMessage());
        }
    }

    /**
     * add json value to limited list
     * the size is fixed
     * first in first out
     */
    public void lAddLimitedList(String key, JSONObject value, int size) {
        try {
            int currentSize = Math.toIntExact(redisTemplate.opsForList().size(key));
            if (currentSize >= size) {
                int overNumber = currentSize - size + 1;
                redisTemplate.opsForList().trim(key, overNumber, -1);
            }
            this.lAdd(key, value);
        } catch (Exception e) {
            log.error("add json value:{} to limited list key:{} error:{}", value.toJSONString(), key, e.getMessage());
        }
    }

    /**
     * add string value to limited list
     * the size is fixed
     * first in first out
     */
    public void lAddLimitedStringList(String key, String value, int size) {
        try {
            int currentSize = Math.toIntExact(redisStringTemplate.opsForList().size(key));
            if (currentSize >= size) {
                int overNumber = currentSize - size + 1;
                redisStringTemplate.opsForList().trim(key, overNumber, -1);
            }
            this.lAdd(key, value);

        } catch (Exception e) {
            log.error("add string value:{} to limited list key:{} error:{}", value, key, e.getMessage());
        }
    }

    /**
     * reset string value to list
     */
    public void lResetString(String key, List<String> values) {
        try {
            this.del(key);
            this.lAddStringList(key, values);
        } catch (Exception e) {
            log.error("reset list key:{} error:{}", key, e.getMessage());
        }
    }

    /**
     * add json value to list
     */
    public void lReset(String key, List<JSONObject> values) {
        try {
            this.del(key);
            this.lAddList(key, values);
        } catch (Exception e) {
            log.error("reset list key:{} error:{}", key, e.getMessage());
        }
    }


    // ============================set=============================

    public void zStringSet(String key, String value, int score) {
        try {
            redisStringTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.error("add set key:{} value:{} score:{} error:{}", key, value, score, e.getMessage());
        }
    }




}
