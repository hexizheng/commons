/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.jscn.commons.core.lang;

/**
 * 错误码
 * 
 * @author 袁兵 2012-3-9
 */
public class ErrorCode {

    /** 系统异常 */
    public static final String ERROR_SYSTEM                            = "error.system";

    /** 查询模板不存在 */
    public static final String ERROR_QUERY_TEMPLATE_NOT_EXIST          = "error.query.template.not.exist";

    /** 生成查询语句出错 */
    public static final String ERROR_BUILD_QUERY_STRING                = "error.build.query.string";

    /** 服务模板执行出现异常 */
    public static final String ERROR_SERVICE_TEMPLATE_EXECUTE          = "error.service.template.execute";

    /** 服务拦截器调用异常 */
    public static final String ERROR_SERVICE_INTERCEPTOR_INVOKE        = "error.service.interceptor.invoke";

    /** 服务拦截器事务执行异常 */
    public static final String ERROR_SERVICE_INTERCEPTOR_TRANS_EXECUTE = "error.service.interceptor.trans.execute";

}
