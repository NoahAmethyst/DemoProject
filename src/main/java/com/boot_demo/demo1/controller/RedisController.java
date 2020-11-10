package com.boot_demo.demo1.controller;


import com.boot_demo.demo1.anotations.RedisLock;
import com.boot_demo.demo1.model.ResponseModel;
import com.boot_demo.demo1.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/getValue")
    public ResponseModel getValue(String key) {
        return ResponseModel.buildSuccess(redisUtil.get(key));
    }

    @PostMapping("/setValue")
    public ResponseModel setValue(String key, @RequestBody Object value) {
        redisUtil.set(key, value);
        return ResponseModel.buildSuccess();
    }

    @GetMapping("/remove")
    public ResponseModel remove(String key) {
        redisUtil.del(key);
        return ResponseModel.buildSuccess();
    }

    @RedisLock(key = "redisLock")
    @GetMapping("/redisLock")
    public ResponseModel redisLock() {
        return ResponseModel.buildSuccess(redisUtil.get("redisLock"));
    }
}
