package com.boot_demo.demo1.service;

import com.boot_demo.demo1.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author ZhonghaoMa
 * @Description: 缓存测试类
 * @date 2020/3/102:26 下午
 */
@Service
public class JedisTestService {


   @Resource
   private JedisPool jedisPool;

    public String get(String key){
        Jedis jedis=jedisPool.getResource();
        jedis.select(1);
        String s=jedis.get(key);
        jedis.close();
        return s;
    }

    public <T> T getObject(String key, TypeReference<T> type){
        T obj=null;
        if (StringUtils.isEmpty(key)){
            return obj;
        }
        Jedis jedis=jedisPool.getResource();
        try {
            jedis.select(1);
            String jsonObj=jedis.get(key);
            obj=JsonUtil.StringToObject(jsonObj,type);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return obj;
    }

    public <T> T getObject(String key,Class<T> clazz){
        T obj=null;
        if (StringUtils.isEmpty(key)){
            return obj;
        }
        Jedis jedis=jedisPool.getResource();
        try {
            jedis.select(1);
            String jsonObj=jedis.get(key);
            obj=JsonUtil.StringToObject(jsonObj,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return obj;
    }

    public boolean add(String key, Object object){
        if (StringUtils.isEmpty(key) || object==null){
            return false;
        }
        Jedis jedis=jedisPool.getResource();
        try {
            jedis.select(1);
            String jsonObj=JsonUtil.objectToString(object);
            jedis.set(key,jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            jedis.close();
        }
        return true;
    }


    public boolean add(String key, Object object,Integer seconds){
        if (StringUtils.isEmpty(key) || object==null){
            return false;
        }
        Jedis jedis=jedisPool.getResource();
        try {
            jedis.select(1);
            String jsonObj=JsonUtil.objectToString(object);
            jedis.set(key,jsonObj);
            jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            jedis.close();
        }
        return true;
    }

    public Long getExpire(String key){
        Long expireTime=null;
        Jedis jedis=jedisPool.getResource();
        try {
            jedis.select(1);
            expireTime=jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return expireTime;
    }



}
