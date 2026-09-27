package com.demo.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@org.springframework.stereotype.Service
@Component
public class StaffService {

    @Reference
    SycService sycService;

    @Reference
    BaoshanService baoshanService;

    public void add(int i) {
        if (i == 1) {
            System.out.println(sycService.sycData());
        } else {
            System.out.println(baoshanService.sysData());
        }

    }
}
