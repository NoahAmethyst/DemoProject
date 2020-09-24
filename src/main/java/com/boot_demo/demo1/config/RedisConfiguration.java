package com.boot_demo.demo1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
//    @Value("${redis.node.maxTotal}")
    private Integer maxTotal=8;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    public JedisPoolConfig jedisPoolConfig(){    //这个是修改redis性能的时候需要的对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);

        return jedisPoolConfig;
    }

    @Bean  //这个注解注入工厂的名称是方法名
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = jedisPoolConfig();
        return new JedisPool(jedisPoolConfig,host,port);
    }
}


