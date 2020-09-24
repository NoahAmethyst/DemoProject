package com.boot_demo.demo1.test.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Async
    public void sellTicket(int i) {
        i = i > 0 ? i : 0;
        for (int j = 0; j < i; j++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(j);
        }
    }
}
