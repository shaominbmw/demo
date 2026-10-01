package com.demo.service;

import com.demo.context.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service
@Slf4j
public class SycServiceImpl implements SycService {

    @Override
    public String sycData() {
        String traceId = TraceContext.get();
        if (traceId == null || traceId.isEmpty()) {
            traceId = "no-trace";
        }
        log.info("[traceId:{}] 武钢数据同步成功", traceId);
        return "武钢数据同步成功";
    }
}
