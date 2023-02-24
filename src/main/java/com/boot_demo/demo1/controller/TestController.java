package com.boot_demo.demo1.controller;

import com.boot_demo.demo1.delay.DelayQueueManager;
import com.boot_demo.demo1.delay.TestDelayService;
import com.boot_demo.demo1.model.ResponseModel;
import com.boot_demo.demo1.service.demo.DemoService;
import com.boot_demo.demo1.utils.ImageUtil;
import com.boot_demo.demo1.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ZhonghaoMa
 * @Description: 测试
 * @date 2020/3/91:09 下午
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private KafkaTemplate kafkaTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ImageUtil imageUtil;

    @Resource
    private TestDelayService testDelayService;
    @Resource
    private DemoService demoService;

    @GetMapping("/hello")
    public ResponseModel fetchDemo() {
        return ResponseModel.buildSuccess("hello");
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendKafka(@RequestBody String message) {
        try {
            log.info("kafka的消息={}" + message);
            kafkaTemplate.send("test", "key", message);
            log.info("发送kafka成功.");
        } catch (Exception e) {
            log.error("发送kafka失败" + e.getMessage());
        }
    }

    @GetMapping(value = "/delayQueue")
    public ResponseModel delayQueue() {
        testDelayService.put("this is a element", 1000);
        return ResponseModel.buildSuccess();
    }


    @GetMapping(value = "/getAllDemo")
    public ResponseModel getAllDemo() {
        ResponseModel response = ResponseModel.buildSuccess();
        response.setData(demoService.getAllDemos());
        return response;
    }

}
