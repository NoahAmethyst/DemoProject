package com.boot_demo.demo1.delay;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestDelayService extends DelayQueueManager<String> {


    @Override
    public void put(String element, long expire) {
        log.info("加入延时任务：{}", JSONObject.toJSONString(element));
        DelayTask<String> delayTask = new DelayTask(element, expire);
        delayTask.setData(element);
        delayQueue.put(delayTask);
    }

    @Override
    void processTask(DelayTask<String> task) {
        log.info("process test delay service:{}", task.getData());
    }
}
