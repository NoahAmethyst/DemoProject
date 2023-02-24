package com.boot_demo.demo1.entity;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
public class BaseEntity {

    private Date createTime;

    private Date updateTime;
}
