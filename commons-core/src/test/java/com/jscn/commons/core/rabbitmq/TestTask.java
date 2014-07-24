package com.jscn.commons.core.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTask implements MessageTask {

	private static final Logger LOG = LoggerFactory.getLogger("AccessLogger");
	
	private int count = 0;
	
	@Override
	public void handleTask(String message) {
		LOG.info("["+Thread.currentThread().getName()+"]处理第"+ ++count +"消息");
	}
}
