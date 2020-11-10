package com.boot_demo.demo1.test.udp;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;

/**
 * IntegrationClientConfig
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/20 15:59
 */
@Configuration
public class UdpIntegrationClientConfig {


    private Integer udpPort = 8080;

    @Bean
    @ServiceActivator(inputChannel = "udpOut")
    public UnicastSendingMessageHandler unicastSendingMessageHandler() {
        UnicastSendingMessageHandler unicastSendingMessageHandler = new UnicastSendingMessageHandler("localhost", udpPort);
        return unicastSendingMessageHandler;
    }

}
