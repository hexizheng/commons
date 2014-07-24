package com.jscn.commons.core.threadlocal;
/**
 * 当前登录用户
 * @author Administrator
 *
 */
public class CurrentUser {

	public final static String USER_TYPE_STORE = "store";
	public final static String USER_TYPE_MANAGER = "manager";
	public final static String USER_TYPE_USER = "user";
	
	private String userId;	//customer or manager  ID
	
	private String userName;
	
	private String userType;   //操作人类型    store manager user
	
	private String ip;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
	
	
}
