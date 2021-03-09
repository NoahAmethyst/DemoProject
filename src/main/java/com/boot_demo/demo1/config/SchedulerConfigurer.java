package com.boot_demo.demo1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@EnableScheduling
@Configuration
@Slf4j
public class SchedulerConfigurer implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(50));
    }

    @Scheduled(cron = "0/5 * * * * ? ", zone = "GMT+8")
    public void timer() {
//        log.info("{}", DateTimeUtil.formatDate(new Date(), DateTimeUtil.TIMESTAMP_FORMAT_STRING_STANDARD));
    }

}
