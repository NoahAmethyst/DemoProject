package com.boot_demo.demo1.service;

import com.boot_demo.demo1.dao.EntityDemoDao;
import com.boot_demo.demo1.entity.EntityDemo;
import com.boot_demo.demo1.model.EntityDemoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EntityDemoService {

    @Resource
    private EntityDemoDao entityDemoDao;

    public List<EntityDemoModel> fetchAll() {
        List<EntityDemoModel> targetList = new ArrayList<>();
        List<EntityDemo> oldList = entityDemoDao.fetchAllDemo();
        if (!CollectionUtils.isEmpty(targetList)) {
            oldList.forEach(item -> {
                targetList.add(EntityDemoModel.build(item));
            });
        }
        return targetList;
    }

    public EntityDemoModel fetchById(String id) {
        return EntityDemoModel.build(entityDemoDao.fetchDemo(id));
    }
}
