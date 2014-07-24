package com.jscn.commons.sso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jscn.commons.core.http.HttpClientUtil;
import com.jscn.commons.core.utils.StringUtils;
/**
 * 自动登录过滤器
 * @author hexizheng
 *
 */
public class AutoSigninFilter implements Filter {



	private static final String SESSION_LOGIN_REDIRECT_URL = "LOGIN_REDIRECT_URL";
	private static final String NO_CAS_FLAG = "NO_CAS_FLAG";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// 转化为http请求
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if(isLogined(req)){
			//session 登录过跳过
			if("true".equals(request.getParameter("sso_logout"))){
				req.getSession().removeAttribute(FilterUtils.SESSION_USER);
				//重新跳转取去除sso_logout 参数
				resp.sendRedirect(req.getRequestURL().toString());
			}else{
				filterChain.doFilter(request, response);
			}
			return;
		}
		
		//如果是 cas  Redirect, 跳转请求页面或登录页面
		if (!casRedirect(req, resp)) {
			return;
		}

		//cas 登录过 本应用没登录过，
		if (isCasLogind(req)) {
			login(FilterUtils.CAS_LOGIN_URL, req, resp);// 跳转了，不能再做filterChain.doFilter
			return;
		}
		filterChain.doFilter(request, response);
	}

	// 判断有没有登录过
	private boolean isLogined(HttpServletRequest request) {
		
		String user = (String) request.getSession().getAttribute(FilterUtils.SESSION_USER);
		if (StringUtils.isBlank(user)) {
			return false;
		}
		return true;
	}

	// 查询cookie中是否有 "CAS_LOGIN_STATE==1";判断是否在其他系统登录过
	private boolean isCasLogind(HttpServletRequest request) {
		String ticket = request.getParameter("ticket");
		if(!StringUtils.isBlank(ticket)){
			return true;
		}
		//防止重复尝试获取ticket
		if(request.getSession().getAttribute(NO_CAS_FLAG)!=null){
			request.getSession().removeAttribute(NO_CAS_FLAG);
			return false;
		}

//		Cookie[] cookies = request.getCookies();
//		Cookie cookie = null;
//		boolean casLoginState = false;
//		if (cookies != null && cookies.length > 0) {
//			for (int i = 0; i < cookies.length; i++) {
//				cookie = cookies[i];
//				if (cookie.getName().equals("CAS_LOGIN_STATE")) {
//					String value = cookie.getValue();
//					if (value.equals("1"))
//						casLoginState = true;
//					break;
//				}
//			}
//		}
		return true;
	}

	// 已经从其他系统登录过，先跳转到cas，cas还会返回到这个 过滤器，并且返回ticket，然后再次访问cas拿到用户信息。
	protected void login(String loginUrl, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		request.getSession().setAttribute(SESSION_LOGIN_REDIRECT_URL,FilterUtils.getFullRequestURL(request));
		response.sendRedirect(getLoginUrl(request));
	}

	
	private String getLoginUrl(HttpServletRequest request){
		return FilterUtils.CAS_LOGIN_URL + "?service=" + FilterUtils.getAppContextPath(request) + FilterUtils.CAS_CALL_BACK_URL+"&gateway=true";
	}
	

	/**
	 * 判断如果是cas返回ticket的页面就凭ticekt 向casserver 索取用户信息，然后跳转之前的请求页面
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final boolean casRedirect(HttpServletRequest request,HttpServletResponse response) throws IOException,
			ServletException {
		
		final String requestUri = request.getRequestURI();
		if (StringUtils.isEmpty(requestUri)
				|| !requestUri.endsWith(FilterUtils.CAS_CALL_BACK_URL)) {
			return true;
		} else {
			// 获取用户信息
			String userName = getUser(request);
			if (StringUtils.isBlank(userName)) {
				request.getSession().setAttribute(NO_CAS_FLAG, NO_CAS_FLAG);
			} else{
				request.getSession().removeAttribute(NO_CAS_FLAG);

			}
			response.sendRedirect((String) request.getSession().getAttribute(SESSION_LOGIN_REDIRECT_URL));
			
			return false;
		}
	}

	private String getUser(HttpServletRequest request) throws IOException {
		String respString = HttpClientUtil.get(getValidUrl(request));
		if (!StringUtils.isBlank(respString) && respString.startsWith("yes")) {
			String[] strArray = respString.split("\n");
			String userName = strArray[1];
			request.getSession().setAttribute(FilterUtils.SESSION_USER, userName);
			return userName;
		}else{
			request.getSession().removeAttribute(FilterUtils.SESSION_USER);//从cas登出时清楚session
		}
		return null;
	}
	private String getValidUrl(HttpServletRequest request){
		 String ticket = request.getParameter("ticket");
		return  FilterUtils.CAS_VALID_URL + "?service=" + FilterUtils.getAppContextPath(request) + FilterUtils.CAS_CALL_BACK_URL + "&ticket="+ ticket;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
