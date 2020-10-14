package com.boot_demo.demo1.service.audit.model;

import lombok.Data;

@Data
public class PicAuditResponse {

    private Double rate;

    private boolean review;

    private String name;

    private Integer label;

    private String tag;

}
