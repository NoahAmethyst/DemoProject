package com.boot_demo.demo1.test.udp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 默认发送方式
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/5/20 15:53
 */
@Service
@Slf4j
public class UdpSimpleClient {


    private Integer udpPort = 4567;

    public void sendMessage(String message) {
        log.info("发送UDP: {}", message);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", udpPort);
        byte[] udpMessage = message.getBytes();
        DatagramPacket datagramPacket = null;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(udpMessage, udpMessage.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        log.info("发送成功");
    }
}
