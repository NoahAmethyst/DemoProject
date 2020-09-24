package com.boot_demo.demo1.model;

import lombok.Data;


@Data
public class Counter {

    private volatile static Counter counter;

    private int count;


    public static Counter getInstance() {
        if (counter == null) {
            synchronized (Counter.class) {
                counter = new Counter();
            }
        }
        return counter;
    }
}
