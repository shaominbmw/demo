package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 线程池配置
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 支持自动继承父线程 TraceContext 的线程池
     * 内部使用自定义配置的 ThreadPoolExecutor，外层包裹 TraceAwareExecutor 实现 traceId 传递
     */
    @Bean("demoExecutor")
    public TraceAwareExecutor demoExecutor() {
        ExecutorService pool = new ThreadPoolExecutor(
                2,                          // 核心线程数
                10,                         // 最大线程数
                60L,                        // 空闲线程存活时间
                TimeUnit.SECONDS,           // 空闲线程超时单位
                new LinkedBlockingQueue<>(100), // 队列容量
                r -> {
                    Thread t = new Thread(r);
                    t.setName("demo-task-thread");
                    t.setDaemon(true);
                    return t;
                },
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略：调用者线程执行
        );
        return new TraceAwareExecutor(pool);
    }
}
