package com.boot_demo.demo1.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Description
 * @Date 2020/6/28
 * @Created by ZhonghaoMa
 */
@Configuration
@ConfigurationProperties(prefix = "kafka-config",ignoreUnknownFields = false)
@PropertySource("classpath:config/kafka-config.properties")
@Component
@Slf4j
public class KafkaConsumer {


    @KafkaListener(topics = {"test"})
    public void consumer(ConsumerRecord<?, ?> consumerRecord) {
        //判断是否为null
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        log.info("record :" + kafkaMessage);
        if (kafkaMessage.isPresent()) {
            //得到Optional实例中的值
            Object message = kafkaMessage.get();
            log.info("消费消息:" + message);
        }
    }

}
