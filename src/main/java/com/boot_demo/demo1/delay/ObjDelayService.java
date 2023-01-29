package com.boot_demo.demo1.delay;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ObjDelayService extends DelayQueueManager<Object> {

    @Override
    void put(Object element, long expire) {

    }

    @Override
    void processTask(DelayTask<Object> task) {

    }
}
