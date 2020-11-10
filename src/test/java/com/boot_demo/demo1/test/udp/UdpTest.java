package com.boot_demo.demo1.test.udp;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UdpTest {

    @Resource
    private UdpSimpleClient udpSimpleClient;

    @Resource
    private UdpServer udpServer;

    @Test
    public void sendUdpMessage() {
        String message = "this is a udp message";
        udpSimpleClient.sendMessage(message);
    }
}
