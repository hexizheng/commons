package com.jscn.commons.core.open;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import com.jscn.commons.core.exceptions.BusinessException;
import com.jscn.commons.core.exceptions.SystemException;
import com.jscn.commons.core.open.OpenResponse;
import com.jscn.commons.core.spring.SpringContextHolder;
import com.jscn.commons.core.utils.ClassUtils;

/**
 * 开放平台接入拦截器，
 * 拦截开放平台的http请求，直接调用服务返回结果
 * @author Administrator
 *
 */
public class OpenServiceFilter implements Filter{
    
    private  final Logger     logger = LoggerFactory.getLogger(getClass());
    private final static String CHARSET = "utf-8";
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest)request;
        req.setCharacterEncoding(CHARSET);
        HttpServletResponse res = (HttpServletResponse)response;
        Map<String,String> paramMap = getServiceParameters(req);
        logger.info("收到请求：[{}]",ClassUtils.getObjectLogInfo(paramMap));
        
        OpenResponse openResponse = invokeService(paramMap, paramMap.get("BEAN_NAME"), paramMap.get("METHOD_NAME"));
        res.setCharacterEncoding(CHARSET);
        if("XML".equalsIgnoreCase(paramMap.get("FORMART"))){
            res.getWriter().write(URLEncoder.encode(openResponse.toXml(),CHARSET));
            logger.info("响应：[{}]",openResponse.toXml());
        }else{
            res.getWriter().write(URLEncoder.encode(openResponse.toJson(),CHARSET));
            logger.info("响应：[{}]",openResponse.toJson());
        }
    }

    /**
     * 调用本地服务
     * @param paramMap
     * @param beanName
     * @param methodName
     * @return
     */
    private OpenResponse invokeService(Map<String, String> paramMap,
            String beanName, String methodName) {
        
        OpenResponse response = new OpenResponse();
        try {
            Object service = SpringContextHolder.getBean(beanName);
            Method method = service.getClass().getDeclaredMethod(methodName,Map.class);
            Object object = method.invoke(service, paramMap);
            response.setResponse(object);
            response.setIsSuccess("true");
        }catch (Exception e) {
            if(e instanceof InvocationTargetException &&((InvocationTargetException) e).getTargetException() instanceof BusinessException ){
                logger.error("调用服务："+beanName+"，方法："+methodName+"，参数："+paramMap+"抛出异常",((InvocationTargetException) e).getTargetException());
                response.setIsSuccess("false");
                response.setErrorMessage(((InvocationTargetException) e).getTargetException().getMessage());
            }else if(e instanceof InvocationTargetException &&((InvocationTargetException) e).getTargetException() instanceof SystemException ){
                logger.error("调用服务："+beanName+"，方法："+methodName+"，参数："+paramMap+"抛出异常",((InvocationTargetException) e).getTargetException());
                response.setIsSuccess("false");
                response.setErrorMessage(((InvocationTargetException) e).getTargetException().getMessage());
            }else{
                logger.error("调用服务："+beanName+"，方法："+methodName+"，参数："+paramMap+"抛出异常",e);
                response.setIsSuccess("false");
                response.setErrorMessage("系统异常");
            }
            
        }
        return response;
    }

    /**
     * 获取所有参数
     * @param request
     * @return
     */
    public static Map<String,String> getServiceParameters(HttpServletRequest request){
         Map<String ,Object> map = WebUtils.getParametersStartingWith(request, "");
         Map<String ,String> retMap = new TreeMap<String,String>();
         Iterator<String> iterator = map.keySet().iterator();
         while(iterator.hasNext()){
             String key = iterator.next();
             String value = map.get(key).toString();
             retMap.put(key, value);
         }
         
         return retMap;
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}
