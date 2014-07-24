package com.jscn.commons.core.rabbitmq;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.MessageProperties;

public class ClientTemplate extends RabbitMqBasic {

	private static final Logger LOG = LoggerFactory.getLogger("com.jscn.commons.core.rabbitmq.ClientTemplate");
	
	/**
	 * 加入消息队列
	 * @param message  消息内容
	 * @throws Exception 
	 */
	public void sendMsg(String message) throws Exception {
		if (isEmpty(message)) {
			LOG.info("resend message is empty");
		}else{
			byte[] msgBytes = this.getCharSet() == null ? message.getBytes()
					: message.getBytes(this.getCharSet());
			reSendMsg(msgBytes,2);
		}
	}

	/**
	 * 加入消息队列
	 * @param messages 消息内容数组
	 * @param delimiter 分隔符 （null:以竖线分隔）
	 * @throws Exception
	 */
	public void sendMsg(String[] messages, String delimiter) throws Exception {
		if (messages == null || messages.length < 1) {
			LOG.info("message is empty");
		}else{
			String msg = joinStrings(messages, delimiter);
			sendMsg(msg);
		}
	}

	/**
	 * 加入消息队列
	 * @param messageBytes  消息内容字节数组
	 * @throws Exception
	 */
	public void sendMsg(byte[] messageBytes) throws Exception {
		
			if (messageBytes == null || messageBytes.length < 1) {
				LOG.info("message is empty");
			}else{
				reSendMsg(messageBytes,2);
			}
	}

	/**
	 * 加入消息队列
	 * @param messageList 字符串类型的消息集合
	 * @throws IOException
	 */
	public void sendMsgList(List<String> messageList) throws Exception {
		if (messageList == null || messageList.size() < 1) {
			LOG.info("message is empty");
		}else{
			for (String message : messageList) {
				sendMsg(message);
			}
		}
	}
	
	/**
	 * 设置路由模式
	 * @param msgBytes 消息字节数组
	 * @throws IOException
	 */
	private void setRouting(byte[] msgBytes) throws IOException {
		// rounting模式
		this.getChannel().exchangeDeclare(EXCHANGE_NAME, "direct");// direct:完全匹配
		for (String routingKey : this.getRoutingKeys().split(",")) {
			LOG.info("[send][routingKey=" + routingKey + "]：" + new String(msgBytes, this.getCharSet()));
			this.getChannel().basicPublish(EXCHANGE_NAME, routingKey, null, msgBytes);
		}
	}
	
	/**
	 * 重发消息
	 * @param message 待重发消息内容
	 * @param reSendNum 重发次数
	 * @throws Exception 
	 */
	private void reSendMsg(byte[] messageBytes,int reSendNum) throws Exception{
		try {
			while(reSendNum>0){
				try {
					// 路由模式
					if (!isEmpty(this.getRoutingKeys()) && this.getRoutingKeys().split(",").length > 0) {
						setRouting(messageBytes);
					} else {
						this.getChannel().queueDeclare(this.getQueueName(),this.isQuePersistence(), false, false, this.getArgs());
						LOG.info("[send to " + this.getQueueName() + "]：" + new String(messageBytes, this.getCharSet()));
						this.getChannel().basicPublish("", this.getQueueName(),this.isMsgPersistence()?MessageProperties.PERSISTENT_TEXT_PLAIN:null, messageBytes);
					}
					reSendNum = 0;
					LOG.info("send message success");
				} catch (Exception e) {
					LOG.error("send message error:", e);
					Thread.sleep(500);
					reSendNum--;
					if(reSendNum==0){
						LOG.info("send message fault");
					}
				}
			}
		} catch (Exception e) {
			LOG.error("send message queue error:", e);
			throw e;
		}
	}
}
