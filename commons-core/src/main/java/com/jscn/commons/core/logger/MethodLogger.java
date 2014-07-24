package com.jscn.commons.core.logger;

import java.util.Random;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jscn.commons.core.utils.ClassUtils;
import com.jscn.commons.core.utils.ThrowableUtils;

/**
 * 为所有方法调用打印日志
 * @author 贺夕政
 *
 */

@Aspect
public class MethodLogger {

	private static final Logger     logger = LoggerFactory.getLogger(MethodLogger.class);
	
	@Around(value = "within(com.jscn..*) && execution(* *..*Service*.*(..))")
	public Object log(ProceedingJoinPoint point ) throws Throwable{
		
		String methodInfo = getMethodInfo(point);
		logger.info(methodInfo);
		logger.info(getParamStr(point.getArgs()));
		
		long beginMillis = System.currentTimeMillis();
		try {
			Object result = point.proceed();
			long endMillis = System.currentTimeMillis();
			String returnInfo = ClassUtils.getObjectLogInfo(result);
			logger.info(methodInfo + "  返回 【" + returnInfo + "】【用时:" + (endMillis - beginMillis) + "毫秒】");
			return result;
		} catch (Throwable t) {
			long endMillis = System.currentTimeMillis();
			String throwableInfo = ThrowableUtils.getThrowableInfo(t);
			logger.error(methodInfo + "  拋出异常 【" + throwableInfo + "】【用时:" + (endMillis - beginMillis) + "毫秒】");
			throw t;
		}
	}
	
	private String getMethodInfo(ProceedingJoinPoint point){
		String threadInfo = "【threadId:" + Thread.currentThread().getId() + "】";
		String methodId = "【callId:"+System.currentTimeMillis()+"-"+new Random().nextInt(1000)+"】";
		String methodSimpleName = "方法【 " + point.getSignature().toShortString() + "()】";
		return threadInfo + methodId + methodSimpleName;
		 
	}
	private String getParamStr(Object[] params){
		if(params==null||params.length==0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("参数：");
		for(Object obj:params){
			sb.append("【"+ClassUtils.getObjectLogInfo(obj)+"】");
		}
		sb.append("");
		return sb.toString();
	}
	
	/*
	public Object invoke(MethodInvocation mi) throws Throwable {
		//整理调用方法的信息;
		String methodId = "【callId:"+System.currentTimeMillis()+"-"+RandomStringUtils.randomNumeric(4)+"】";
		String userInfo = ThreadLocalUtils.getUserId()!=null?"【userId:"+ThreadLocalUtils.getUserLoginName()+"-"+ThreadLocalUtils.getUserId()+"】":"";
		String ipInfo = ThreadLocalUtils.getClientIp()!=null?"【ip:"+ThreadLocalUtils.getClientIp()+"】":"";
		String threadInfo = "【threadId:" + Thread.currentThread().getId() + "】";
		String methodFullName = "【 " + AopClassUtils.getMethodCallInfo(mi) + "】";
		String methodSimpleName = "【 " + AopClassUtils.getMethodSimpleName(mi) + "()】";
		String methodFullInfo =threadInfo + userInfo + ipInfo + methodId + methodFullName;
		String methodSimpleInfo =threadInfo + userInfo + ipInfo + methodId + methodSimpleName;

		//在调用方法前后进行日志输出
		logger.info(methodFullInfo );
		long beginMillis = System.currentTimeMillis();
		try {
			Object result = mi.proceed();
			long endMillis = System.currentTimeMillis();
			String returnInfo = mi.getMethod().getReturnType().equals(void.class)?"void":ClassUtils.getObjectLogInfo(result);
			logger.info(methodSimpleInfo + "  return 【" + returnInfo + "】【用时:" + (endMillis - beginMillis) + "毫秒】");
			return result;
		} catch (Throwable t) {
			String throwableInfo = ThrowableUtils.getThrowableInfo(t);
			long endMillis = System.currentTimeMillis();
			logger.error(methodSimpleInfo + "  throw 【" + throwableInfo + "】【用时:" + (endMillis - beginMillis) + "毫秒】");
			throw t;
		}

	}
	*/
}
