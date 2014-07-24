package com.jscn.commons.core.rabbitmq;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-test-rabbitmq.xml" })
public class ClientTemplateTest {

	private static final Logger LOG = LoggerFactory.getLogger("AccessLogger");
	@Autowired
	ClientTemplate clientTemplate;
	
	@Test
	public void testSend() throws IOException {
		for(int i=1;i<10;i++){
			long no = 1000000+i;
			try {
				//clientTemplate.sendMsg(new String[]{"流水号："+no,"时间："+System.currentTimeMillis(),"消息类型：注册短信","消息内容：13913779543"}, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		clientTemplate.destroy();
	}
}
