package com.jscn.commons.core.rabbitmq;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

/**
 * 消息任务处理接口
 * @author maojr
 * @date 2012-01-18
 */
public interface MessageTask {

	/**
	 * 处理消息任务的抽象方法
	 * @param message
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws Exception 
	 */
	public void handleTask(String message) throws Exception;
}
