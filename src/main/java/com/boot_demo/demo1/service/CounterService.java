package com.boot_demo.demo1.service;

import com.boot_demo.demo1.model.Counter;
import org.springframework.stereotype.Service;

@Service
public class CounterService {


    public void addCounter(int i) {
        Counter counter = Counter.getInstance();
        i = i > 0 ? i : 0;
        counter.setCount(counter.getCount() + i);
    }

    public int statistCount() {
        Counter counter = Counter.getInstance();
        return counter.getCount();
    }
}
