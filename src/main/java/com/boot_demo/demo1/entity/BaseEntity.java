package com.boot_demo.demo1.entity;


import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    private Date createTime;

    private Date updateTime;
}
