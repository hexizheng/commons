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

import com.jscn.commons.core.utils.StringUtils;
/**
 * 操作管理
 * @author Administrator
 *
 */
public class SessionFilter implements Filter  {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if(!isLogined(request)){
			((HttpServletResponse) resp).sendRedirect(FilterUtils.getLoginUrl(request));
		}
		else{
			filterChain.doFilter(req, resp);
		}
	}
	// 判断有没有登录过
		private boolean isLogined(HttpServletRequest request) {
			String user = (String) request.getSession().getAttribute(FilterUtils.SESSION_USER);
			if (StringUtils.isBlank(user)) {
				return false;
			}
			return true;
		}
		
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
