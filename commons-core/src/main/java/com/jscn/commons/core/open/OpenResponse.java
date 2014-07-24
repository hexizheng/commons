package com.jscn.commons.core.open;

import com.jscn.commons.core.utils.JsonUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("jscn")
public class OpenResponse {

	private String isSuccess;
	private String errorMessage;
	private Object response;
	
	
	
	public  String toJson(){
		setEmpty();
		return JsonUtil.objectToJson(this);
	}

	public  String toXml(){
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.aliasSystemAttribute(null, "class");
		setEmpty();
		return xstream.toXML(this);
	}
	/**
	 * 用于保持json xml输出空置时 内容一致
	 */
	private void setEmpty(){
		if(response==null)response="";	//生成 空response
		if(errorMessage==null)errorMessage="";	//生成 空errorMessage
	}
	public String getIsSuccess() {
		return isSuccess;
	}



	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	
	
}
