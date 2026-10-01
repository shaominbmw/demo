package com.demo.interceptor;

import com.demo.context.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 链路追踪拦截器
 * 从前端请求 Header 中提取 traceId；如果没传则自动生成。
 * 存入 ThreadLocal（TraceContext）和 RpcContext（Dubbo attachment），供后续日志记录与 RPC 透传使用。
 */
@Slf4j
@Component
public class TraceInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID_HEADER = "traceId";
    public static final String TRACE_ID_KEY = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(TRACE_ID_HEADER);
        // 如果前端没传，自动生成
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString().replace("-", "");
            log.info("[TraceInterceptor] Generate new traceId: {}", traceId);
        } else {
            log.info("[TraceInterceptor] Get traceId from header: {}", traceId);
        }
        // 存入 ThreadLocal，供 Controller / Service 层读取
        TraceContext.set(traceId);
        // 放到 RpcContext attachments 中，Dubbo Consumer 调用时自动传递到 Provider
        RpcContext.getContext().setAttachment(TRACE_ID_KEY, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理资源，避免线程复用导致数据污染
        TraceContext.remove();
        RpcContext.getContext().setAttachment(TRACE_ID_KEY, "");
        log.info("[TraceInterceptor] Clean up trace context");
    }
}
