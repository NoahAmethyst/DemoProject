package com.boot_demo.demo1.test.entity;

public class SingleTone {

    private static SingleTone singleton;

    public static SingleTone getInstance() {
        if (singleton == null) {
            singleton = new SingleTone();
        }
        return singleton;
    }


    public Object getInfo(Object object) {
        return object;
    }
}
