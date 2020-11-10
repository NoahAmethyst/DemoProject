package com.boot_demo.demo1.service.audit.udp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayRawSerializer;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.messaging.Message;

@Configuration
@Slf4j
public class DataReceiveConfigration {


    @Bean
    public UnicastReceivingChannelAdapter getUnicastReceivingChannelAdapter() {
        Integer port = 4567;
        log.info("init udp server,port:{}", port);
        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(port);//实例化一个udp 4567端口
        adapter.setOutputChannelName("udp");
        return adapter;
    }

    @Transformer(inputChannel = "udp", outputChannel = "udpString")
    public String transformer(Message<?> message) {
        return new String((byte[]) message.getPayload());//把接收的数据转化为字符串
    }

    @Filter(inputChannel = "udpString", outputChannel = "udpFilter")
    public boolean filter(String message) {
        return message.startsWith("abc");//如果接收数据开头不是abc直接过滤掉
    }


    @Router(inputChannel = "udpFilter")
    public String routing(String message) {
        if (message.contains("1")) {//当接收数据包含数字1时
            return "udpRoute1";
        } else {
            return "udpRoute2";
        }
    }


    @ServiceActivator(inputChannel = "udpRoute1")
    public void udpMessageHandle(String message) {
        System.out.println("udp1:" + message);
    }


    @ServiceActivator(inputChannel = "udpRoute2")
    public void udpMessageHandle2(String message) {
        System.out.println("udp2:" + message);
    }


    @Bean
    public TcpNetServerConnectionFactory getServerConnectionFactory() {
        TcpNetServerConnectionFactory serverConnectionFactory = new TcpNetServerConnectionFactory(1234);
        serverConnectionFactory.setSerializer(new ByteArrayRawSerializer());
        serverConnectionFactory.setDeserializer(new ByteArrayRawSerializer());
        serverConnectionFactory.setLookupHost(false);
        return serverConnectionFactory;
    }


    @Bean
    public TcpReceivingChannelAdapter getReceivingChannelAdapter() {
        TcpReceivingChannelAdapter receivingChannelAdapter = new TcpReceivingChannelAdapter();
        receivingChannelAdapter.setConnectionFactory(getServerConnectionFactory());
        receivingChannelAdapter.setOutputChannelName("tcp");
        return receivingChannelAdapter;
    }


    @ServiceActivator(inputChannel = "tcp")
    public void messageHandle(Message<?> message) {
        System.out.println(new String((byte[]) message.getPayload()));
    }


}
