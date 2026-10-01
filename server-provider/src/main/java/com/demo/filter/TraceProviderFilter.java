package com.demo.filter;

import com.demo.context.TraceContext;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Dubbo Provider 侧过滤器
 * 从 RpcContext.attachments 中获取上游传递的 traceId。
 * 如果上游已经携带了 traceId，使用上游的；否则生成一个新的 UUID。
 */
@Activate(group = "provider")
public class TraceProviderFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TraceProviderFilter.class);

    public static final String TRACE_ID_KEY = "traceId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = invocation.getAttachment(TRACE_ID_KEY);
        // 如果上游没有携带 traceId，则生成一个新的
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString().replace("-", "");
            logger.info("[TraceProviderFilter] No upstream traceId, generate new: {}", traceId);
        } else {
            logger.info("[TraceProviderFilter] Use upstream traceId: {}", traceId);
        }
        // 同时放到 invocation、RpcContext 和 TraceContext 中
        invocation.setAttachment(TRACE_ID_KEY, traceId);
        RpcContext.getContext().setAttachment(TRACE_ID_KEY, traceId);
        TraceContext.set(traceId);

        try {
            return invoker.invoke(invocation);
        } finally {
            // 请求结束后清理 ThreadLocal
            TraceContext.remove();
        }
    }
}
