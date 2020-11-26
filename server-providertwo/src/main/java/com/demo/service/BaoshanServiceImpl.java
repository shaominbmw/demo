package com.demo.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service
public class BaoshanServiceImpl implements BaoshanService {

    @Override
    public String sysData() {
        return "宝山数据同步成功";
    }
}
