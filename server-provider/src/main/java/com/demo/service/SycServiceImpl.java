package com.demo.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service
public class SycServiceImpl implements SycService {

    @Override
    public String sycData() {
        return "武钢数据同步成功";
    }
}
