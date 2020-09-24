package com.boot_demo.demo1.test.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Counter {

    private int count;
}
