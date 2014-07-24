package com.jscn.commons.core.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息队列基础类
 * @author maojr
 * @date 2012-01-18
 */
public class RabbitMqBasic {

    private static final Logger LOG              = LoggerFactory.getLogger("com.jscn.commons.core.rabbitmq.RabbitMqBasic");

    /**
     * EXCHANGE_NAME 默认：direct_logs
     */
    public final String         EXCHANGE_NAME    = "direct_logs";

    /**
     * 消息队列连接工厂
     */
    private ConnectionFactory   factory          = null;

    /**
     * 消息队列连接
     */
    private Connection          connection       = null;

    /**
     * 消息队列通道
     */
    private Channel             channel          = null;

    /**
     * 队列镜像设置参数集合
     */
    private Map<String, Object> args             = null;

    /**
     * 获取连接的集群地址集合字符串
     */
    private String              addressList      = null;

    /**
     * 消息队列名
     */
    private String              queueName        = null;

    /**
     * 消息队列主机服务名
     */
    private String              serverName       = null;

    /**
     * 路由模式关键字，多个关键字以逗号分隔
     */
    private String              routingKeys      = null;

    /**
     * 消息的字符集编码
     */
    private String              charSet          = "utf-8";

    /**
     * 消息队列是否持久化
     */
    private boolean             isQuePersistence = false;

    /**
     * 消息内容是否持久化
     */
    private boolean             isMsgPersistence = false;

    /**
     * 消息保存时间
     */
    private long                msgsaveTimeout   = 0;

    /**
     * 线程池大小
     */
    private int                 threadPoolSize   = 0;

    /**
     * 获取消息通道
     * @return
     */
    public Channel getChannel() {
        this.createRabbitMq();
        return channel;
    }

    /**
     * 获取集群地址集合字符串，多个地址用“|”分隔
     */
    public String getAddressList() {
        return addressList;
    }

    /**
     * 设置集群地址集合字符串，多个地址用“|”分隔
     */
    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

    /**
     * 获取队列镜像的设置参数
     * @return
     */
    public Map<String, Object> getArgs() {
        return args;
    }

    /**
     * 获取队列名
     * @return
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * 设置队列名
     * @return
     */
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    /**
     * 获取队列主机名
     * @return
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * 设置队列主机名
     * @return
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * 获取路由关键字
     * @return
     */
    public String getRoutingKeys() {
        return routingKeys;
    }

    /**
     * 设置路由关键字
     * @return
     */
    public void setRoutingKeys(String routingKeys) {
        this.routingKeys = routingKeys;
    }

    /**
     * 获取字符集
     * @return
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * 设置字符集
     * @return
     */
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    /**
     * 获取队列是否持久化
     * @return
     */
    public boolean isQuePersistence() {
        return isQuePersistence;
    }

    /**
     * 设置队列是否持久化
     * @return
     */
    public void setQuePersistence(boolean isQuePersistence) {
        this.isQuePersistence = isQuePersistence;
    }

    /**
     * 获取消息是否持久化
     * @return
     */
    public boolean isMsgPersistence() {
        return isMsgPersistence;
    }

    /**
     * 设置消息是否持久化
     * @return
     */
    public void setMsgPersistence(boolean isMsgPersistence) {
        this.isMsgPersistence = isMsgPersistence;
    }

    /**
     * @return the msgsaveTimeout
     */
    public long getMsgsaveTimeout() {
        return msgsaveTimeout;
    }

    /**
     * @param msgsaveTimeout the msgsaveTimeout to set
     */
    public void setMsgsaveTimeout(long msgsaveTimeout) {
        this.msgsaveTimeout = msgsaveTimeout;
    }

    /**
     * 获取线程池大小
     * @return
     */
    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    /**
     * 设置线程池大小
     * @param threadPoolSize
     */
    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    /**
     * 创建消息队列
     */
    private void createRabbitMq() {
        // 线程池服务对象
        ExecutorService es = null;
        try {
            if (this.factory == null) {
                factory = new ConnectionFactory();
                // 设置队列镜像
                args = new HashMap<String, Object>();
                args.put("x-ha-policy", "all");
                //设置消息保存超时时间
                if (msgsaveTimeout > 0)
                    args.put("x-message-ttl", msgsaveTimeout);
            }
            if (this.channel == null || !this.channel.isOpen() || this.connection == null
                    || !this.connection.isOpen()) {
                if (isEmpty(this.serverName) && isEmpty(this.addressList)) {
                    LOG.info("message queue server_name is empty");
                    return;
                }
                if (!isEmpty(this.serverName)) {// 独立服务器
                    this.factory.setHost(this.serverName);
                    if (this.threadPoolSize > 0) {
                        es = Executors.newFixedThreadPool(this.threadPoolSize);
                        this.connection = this.factory.newConnection(es);
                    } else {
                        this.connection = this.factory.newConnection();
                    }
                } else {// 集群方式
                    String[] addressArr = this.addressList.split("\\|");
                    Address[] addrArr = new Address[addressArr.length];
                    for (int i = 0; i < addressArr.length; i++) {
                        String[] address = addressArr[i].split(":");
                        addrArr[i] = new Address(address[0], Integer.parseInt(address[1]));
                    }
                    if (this.threadPoolSize > 0) {
                        es = Executors.newFixedThreadPool(this.threadPoolSize);
                        this.connection = this.factory.newConnection(es, addrArr);
                    } else {
                        this.connection = this.factory.newConnection(addrArr);
                    }
                }
                if (this.connection.isOpen()) {
                    LOG.info("connect remote host[" + this.connection.getAddress().getHostAddress()
                            + "]success");
                    this.channel = this.connection.createChannel();
                    LOG.info("local host[" + this.connection.getAddress().getLocalHost()
                            + "]create message channel complete...");
                }
            }
        } catch (Exception e) {
            LOG.error("rabbitMq init error:", e);
        }
    }

    /**
     * 销毁消息队列
     */
    public void destroy() {
        try {
            if (this.channel != null) {
                this.channel.close();
                this.channel = null;
            }
            if (this.connection != null) {
                this.connection.close();
                this.connection = null;
            }
            this.factory = null;
            LOG.info("message queue client has destoryed");
        } catch (IOException e) {
            LOG.error("rabbitMq destroy error:", e);
        }
    }

    /**
     * 判断字符串是否为空
     * @param obj
     * @return
     */
    protected boolean isEmpty(String string) {
        return string == null || "".equals(string.trim());
    }

    /**
     * 将字符串数组中得元素按指定分隔符拼接成字符串
     * @param strings 字符串数组
     * @param delimiter 指定分隔符
     * @return
     */
    protected String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0)
            return "";
        if (isEmpty(delimiter))
            delimiter = "|";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
