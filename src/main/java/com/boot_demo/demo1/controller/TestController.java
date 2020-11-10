package com.boot_demo.demo1.controller;

import com.boot_demo.demo1.delay.DelayQueueManager;
import com.boot_demo.demo1.delay.TaskElement;
import com.boot_demo.demo1.model.ResponseModel;
import com.boot_demo.demo1.service.CounterService;
import com.boot_demo.demo1.service.EntityDemoService;
import com.boot_demo.demo1.service.ExcelOptionsService;
import com.boot_demo.demo1.service.JedisTestService;
import com.boot_demo.demo1.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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
    private ExcelOptionsService excelOptionsService;

    @Resource
    private JedisTestService jedisTestService;

    @Resource
    private CounterService counterService;

    @Resource
    private EntityDemoService entityDemoService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private DelayQueueManager delayQueueManager;

    @Value("${ActiveMQ.queueName}")
    private String queueName;

    @Value("${ActiveMQ.queueName2}")
    private String queueName2;

    @Value("${ActiveMQ.topicName}")
    private String topicName;


    @GetMapping("/hello")
    public ResponseModel fetchDemo() {
        return ResponseModel.buildSuccess("hello");
    }

    @GetMapping("/fetchDemo")
    public ResponseModel fetchDemo(String id) {
        ResponseModel responseModel;
        if (StringUtils.isNotEmpty(id)) {
            responseModel = ResponseModel.buildSuccess(entityDemoService.fetchById(id).getJsonValue());
        } else {
            responseModel = ResponseModel.buildSuccess(entityDemoService.fetchAll());
        }
        return responseModel;

    }


    /**
     * excel文件的上传
     */
    @PostMapping("/upload")
    public List<Object> upload(MultipartFile file) throws IOException {
        return excelOptionsService.readExcel(file.getInputStream());
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

    @RequestMapping(value = "/statist", method = RequestMethod.GET)
    public ResponseModel statistTest() {
        ResponseModel responseModel = ResponseModel.builder()
                .status(200)
                .build();
        try {
            counterService.addCounter(1);
            responseModel.setData(counterService.statistCount());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return responseModel;
    }

    @GetMapping(value = "/delayQueue")
    public ResponseModel delayQueue() {
        for (int i = 0; i < 10; i++) {
            delayQueueManager.put(new TaskElement("this is element:" + i));
        }
        return ResponseModel.buildSuccess();
    }

}
