package com.jscn.commons.sso.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.jscn.commons.core.exceptions.SystemException;

public class FilterUtils {
	public static final int DEFAULT_WEB_PORT = 80;
	public static final String CAS_LOGOUT_URL = "https://sso.test.com/cas/logout";
	public static final String SESSION_USER = "USER_NAME";
	public static final String CAS_LOGIN_URL = "https://sso.test.com/cas/login";
	public static final String CAS_CALL_BACK_URL = "/sso/postticket.valid";
	public static final String CAS_VALID_URL = "https://sso.test.com/cas/validate";
	

	/**
	 * 取得HTTP请求参数
	 * 
	 * @param request
	 *            http请求
	 * @return http请求的信息{格式(例):[?name=lin]}
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestInfo(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		if (null != parameterMap && !parameterMap.isEmpty()) {
			StringBuilder sb = new StringBuilder("?");
			for (Iterator<Entry<String, String[]>> ite = parameterMap
					.entrySet().iterator(); ite.hasNext();) {
				Entry<String, String[]> paramEntry = ite.next();
				String paramName = paramEntry.getKey();
				String[] paramValueArr = paramEntry.getValue();
				String paramValue = (paramValueArr.length > 0 ? paramValueArr[0]
						: "");
				sb.append(paramName + "=" + paramValue
						+ (ite.hasNext() ? "&" : ""));
			}
			return sb.toString();
		}
		return "";
	}
	
	public static String getAppContextPath(HttpServletRequest request) {
		String portStr = request.getServerPort() == DEFAULT_WEB_PORT ? "" : ":"
				+ request.getServerPort();
		return request.getScheme() + "://" + request.getServerName() + portStr
				+ request.getContextPath();
	}
	
	/**
	 * 
	 * @param request
	 * @param backUrl  返回url
	 * @return
	 */
	public static String getLogoutUrl(HttpServletRequest request,String backUrl){
		//TODO
		return CAS_LOGOUT_URL+"?service="+encodeUrl(backUrl+"?sso_logout=true");
	}
	
	public static String getFullRequestURL(HttpServletRequest request){
		return request.getRequestURL()+FilterUtils.getRequestInfo(request);
	}
	
	public static String getLoginUrl(HttpServletRequest request) {
		return CAS_LOGIN_URL + "?service=" +encodeUrl(getFullRequestURL(request));
	}

	public static String encodeUrl(String url){
		try {
			return URLEncoder.encode(url,"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e);
		}
	}
	public static void main(String args[]){
		System.out.println(encodeUrl("http://backend.test.com/casserver-test3/index.htm?logout=true"));
	}

}
