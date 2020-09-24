package com.boot_demo.demo1.model;

import com.boot_demo.demo1.entity.EntityDemo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityDemoModel {

    private String id;

    private String comment;

    private String value;

    private Object jsonValue;

    public static EntityDemoModel build(EntityDemo entityDemo) {
        return EntityDemoModel.builder()
                .id(entityDemo.getId())
                .value(entityDemo.getValue())
                .comment(entityDemo.getComment())
                .jsonValue(entityDemo.getJsonValue())
                .build();
    }

}
