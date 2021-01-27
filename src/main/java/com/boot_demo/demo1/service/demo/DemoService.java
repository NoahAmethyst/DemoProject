package com.boot_demo.demo1.service.demo;


import com.boot_demo.demo1.dao.DemoMapper;
import com.boot_demo.demo1.model.demo.DemoModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    @Resource
    private ModelMapper modelMapper;


    public List<DemoModel> getAllDemos() {
        return modelMapper.map(demoMapper.selectAll(), new TypeToken<List<DemoModel>>() {
        }.getType());
    }
}
