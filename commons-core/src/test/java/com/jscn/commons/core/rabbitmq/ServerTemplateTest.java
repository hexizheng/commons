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
public class ServerTemplateTest {

	private static final Logger LOG = LoggerFactory.getLogger("AccessLogger");
	@Autowired
	ServerTemplate serverTemplate;
	
	@Test
	public void testSend() throws IOException, InterruptedException {
		//serverTemplate.recMsg(new TestTask());
	}
}
