package com.boot_demo.demo1.entity;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "entityDemo")
public class EntityDemo {

    private String id;

    private String value;

    private String comment;

    private Object jsonValue;
}
