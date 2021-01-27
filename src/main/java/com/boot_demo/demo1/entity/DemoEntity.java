package com.boot_demo.demo1.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "demo")
public class DemoEntity extends BaseEntity {

    @Id
    //when id is auto increasing
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    //you can map db fields to java fields if they are different
    @Column(name = "value")
    private String value;
}
