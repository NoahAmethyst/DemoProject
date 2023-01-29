package com.boot_demo.demo1.test;


import com.boot_demo.demo1.test.service.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
public class TestDemo {

    @Resource
    private TicketService ticketService;


    @Test
    public void asynTest() {
        System.out.println("这是一个异步测试，这里是开始");
        ticketService.sellTicket(5);
        System.out.println("异步测试结束");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void equalTest() {
        Integer a = 128, b = 128, c = 127, d = 127;
        System.out.println(a == b);
        System.out.println(c == d);
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}
