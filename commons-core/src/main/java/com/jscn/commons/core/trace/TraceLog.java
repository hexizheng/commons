/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.trace;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * 跟踪日志
 * 
 * @author 袁兵 2012-3-22
 */
public class TraceLog {

    /** logger */
    protected Logger        logger;

    /** 调用方法名 */
    protected String        method;

    /** 调用时间阈值 */
    protected long          threshold = 0L;

    /** 调用流水号 */
    protected String        invokeNo;

    /** 异常类型 */
    protected String        exType    = "N";

    /** 超过调用时间阈值 */
    protected String        beyondThd = "N";

    /** 开始时间 */
    protected long          beginTime;

    /** 结束时间 */
    protected long          endTime;

    /** 日志信息 */
    protected StringBuilder message   = new StringBuilder();

    /**
     * 构造函数
     * @param logger logger
     * @param method 调用方法名
     * @param threshold 调用时间阈值
     */
    public TraceLog(Logger logger, String method, long threshold) {
        this.logger = logger;
        this.method = method;
        this.threshold = threshold;
        this.invokeNo = MDC.get("invokeNo");
    }

    /**
     * 开始
     */
    public void begin() {
        this.beginTime = System.currentTimeMillis();

        // 产生调用流水号放入MDC
        if (this.invokeNo == null) {
            MDC.put("invokeNo", UUID.randomUUID().toString().replace("-", ""));
        }
    }

    /**
     * 结束
     */
    public void end() {
        if (this.logger.isWarnEnabled()) {
            this.endTime = System.currentTimeMillis();
            long runTime = this.endTime - this.beginTime;

            if (threshold > 0L && runTime > threshold) {
                this.beyondThd = "Y";
            }

            // 日志格式：方法名|执行时间（毫秒）|是否超过阈值|异常类型
            // 日志示例：ME:CardBiz.register|RT:1211|BT:Y|ET:N
            this.message.append("ME:").append(this.method).append("|RT:").append(runTime)
                    .append("|BT:").append(this.beyondThd).append("|ET:").append(this.exType);
        }
    }

    /**
     * 重置
     * @param method 调用方法名
     * @param threshold 调用时间阈值
     */
    public void reset(String method, long threshold) {
        this.method = method;
        this.threshold = threshold;
        this.exType = "N";
        this.beyondThd = "N";
    }

    /**
     * 设置异常类型
     * @param exType 异常类型
     */
    public void setExType(String exType) {
        this.exType = exType;
    }

    /**
     * 记日志
     */
    public void log() {
        try {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn(this.message.toString());
            }
        } finally {
            // 从MDC删除调用流水号
            if (this.invokeNo == null) {
                MDC.remove("invokeNo");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.message.toString();
    }

}
