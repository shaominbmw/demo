package com.demo.config;

import com.demo.context.TraceContext;

import java.util.concurrent.*;

/**
 * 支持自动继承父线程 TraceContext 的线程池代理
 * 提交任务到该线程池时，会自动将父线程的 traceId 传递到子线程
 */
public class TraceAwareExecutor implements Executor {

    private final ExecutorService delegate;

    public TraceAwareExecutor(ExecutorService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void execute(Runnable command) {
        String parentTraceId = TraceContext.get();
        // 包装任务，在子线程中恢复 traceId
        delegate.execute(() -> {
            try {
                TraceContext.set(parentTraceId);
                command.run();
            } finally {
                TraceContext.remove();
            }
        });
    }

    /**
     * 包装 FutureTask 以支持异步结果
     */
    public <T> Future<T> submit(Callable<T> task) {
        String parentTraceId = TraceContext.get();
        Callable<T> wrapped = () -> {
            try {
                TraceContext.set(parentTraceId);
                return task.call();
            } finally {
                TraceContext.remove();
            }
        };
        return delegate.submit(wrapped);
    }
}
