package com.demo.context;

/**
 * 链路追踪上下文，基于 ThreadLocal 存储 traceId，
 * 使得 HTTP 请求层和 Dubbo RPC 层可以在同一线程内共享 traceId。
 */
public class TraceContext {

    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static void set(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String get() {
        return TRACE_ID.get();
    }

    public static void remove() {
        TRACE_ID.remove();
    }
}
