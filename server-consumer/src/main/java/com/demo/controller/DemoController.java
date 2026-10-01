package com.demo.controller;

import com.demo.config.TraceAwareExecutor;
import com.demo.context.TraceContext;
import com.demo.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class DemoController {

    @Autowired
    StaffService staffService;

    @Resource(name = "demoExecutor")
    private TraceAwareExecutor demoExecutor;

    @RequestMapping("/demo")
    public String demo() throws Exception {
        String traceId = TraceContext.get();
        log.info("[traceId:{}] start", traceId);
        staffService.add(1);
        log.info("[traceId:{}] end", traceId);

        // 在新线程中打印日志，TraceAwareExecutor 会自动传递 traceId
        demoExecutor.execute(() -> {
            log.info("[traceId:{}] hello from async", TraceContext.get());
        });

        // 等待异步任务完成
        Thread.sleep(2000);
        return "ok";
    }
}
