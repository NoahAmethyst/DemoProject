package com.boot_demo.demo1.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Service
@Configuration
@Slf4j
public class SystemUtil implements ApplicationRunner {

    public static int cupNum;

    @Value("${custom.env}")
    private String env;

    @Resource
    private RedisUtil redisUtil;

    public String getEnv() {
        return env;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            log.info("last start time in china:{}", redisUtil.getString("redisRun"));
            redisUtil.setString("redisRun", bjSdf.format(new Date(System.currentTimeMillis())));
            cupNum = Runtime.getRuntime().availableProcessors();
            log.info("current cpu number:{}  environment:{}  timezone:{}", cupNum, env, TimeZone.getDefault().getDisplayName());
        } catch (Exception e) {
            log.error("system have error:{}", e.getMessage());
        }
    }

}
