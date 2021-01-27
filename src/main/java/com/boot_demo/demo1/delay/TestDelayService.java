package com.boot_demo.demo1.delay;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestDelayService<String> extends DelayQueueManager {


    @Override
    void processTask(DelayTask task) {
        log.info("run delay task.");
    }
}
