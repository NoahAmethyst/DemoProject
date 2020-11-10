package com.boot_demo.demo1.test.util;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TreadTest {

    private static Integer ticket = 1000;


    @Slf4j
    public static class SoldTicket implements Runnable {

        private static final String TICKET = "TICKET";

        @Override
        public void run() {
            while (ticket > 0) {
                synchronized (TICKET) {
                    log.info(Thread.currentThread().getName() + " sold target,{}", ticket);
                    ticket--;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new SoldTicket(), "window1");
        Thread thread2 = new Thread(new SoldTicket(), "window2");
        Thread thread3 = new Thread(new SoldTicket(), "window3");
        Thread thread4 = new Thread(new SoldTicket(), "window4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
