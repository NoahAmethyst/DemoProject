package com.boot_demo.demo1.test.udp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Integration发送方式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/20 15:53
 */
@Service
@Slf4j
public class UdpIntegrationClient {


    @Resource
    private UnicastSendingMessageHandler unicastSendingMessageHandler;

    public void sendMessage(String message) {
        log.info("发送UDP: {}", message);
        unicastSendingMessageHandler.handleMessage(MessageBuilder.withPayload(message).build());
        log.info("发送成功");
    }

}
