package com.boot_demo.demo1.dao;

import com.boot_demo.demo1.config.JsonTypeHandler;
import com.boot_demo.demo1.entity.EntityDemo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EntityDemoDao extends Mapper<EntityDemo> {

    @Results(@Result(column = "jsonValue", property = "jsonValue", typeHandler = JsonTypeHandler.class))
    @Select("SELECT * FROM entityDemo WHERE id = #{id}")
    EntityDemo fetchDemo(String id);

    @Results(@Result(column = "jsonValue", property = "jsonValue", typeHandler = JsonTypeHandler.class))
    @Select("SELECT * FROM entityDemo")
    List<EntityDemo> fetchAllDemo();
}
