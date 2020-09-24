package com.boot_demo.demo1.test.service;

import com.boot_demo.demo1.test.model.Counter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CounterService {

    @Resource
    private Counter counter;

    public void addCounter(int i) {
        i = i > 0 ? i : 0;
        counter.setCount(counter.getCount() + i);
    }

    public int statistCount(){
        return counter.getCount();
    }
}
