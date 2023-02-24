package com.boot_demo.demo1.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Slf4j
public class TicketService {


    private static int num = 100;
    private ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();


    @Async
    public void sellTicket() {
        if (num > 0) {
            lock.lock();
            num -= 1;
            log.info("thread{} remain tickets num {}", Thread.currentThread().getName(), num);
            lock.unlock();
        }
    }

    @Async
    public void sellTicket2() {
        while (true) {
            synchronized (this) {
                if (num <= 0) {
                    break;
                }
                num -= 1;
                log.info("thread{} remain tickets num {}", Thread.currentThread().getName(), num);

            }
        }
    }


//    @Override
//    public void run() {
//        synchronized (this) {
//            sellTicket2();
//        }
//    }
}
