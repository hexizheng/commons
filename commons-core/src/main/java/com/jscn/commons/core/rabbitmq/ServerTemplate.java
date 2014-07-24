package com.jscn.commons.core.rabbitmq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.QueueingConsumer;

public class ServerTemplate extends RabbitMqBasic {

    private static final Logger LOG       = LoggerFactory
                                                  .getLogger("com.jscn.commons.core.rabbitmq.ServerTemplate");

    /**
     * 是否自动应答
     */
    private boolean             isAutoAsk = false;

    /**
     * 消息接收次数，默认一次
     */
    private int                 basicQos  = 1;

    /**
     * 获取是否自动应答一次
     * 
     * @return
     */
    public boolean isAutoAsk() {
        return isAutoAsk;
    }

    /**
     * 设置是否自动应答一次
     * 
     * @return
     */
    public void setAutoAsk(boolean isAutoAsk) {
        this.isAutoAsk = isAutoAsk;
    }

    /**
     * 获取是否接收一次消息
     * 
     * @return
     */
    public int getBasicQos() {
        return basicQos;
    }

    /**
     * 设置是否接收一次消息
     * 
     * @return
     */
    public void setBasicQos(int basicQos) {
        this.basicQos = basicQos;
    }

    /**
     * 路由形式从消息队列取出消息并进行处理
     * 
     * @param msgTask
     * @throws IOException
     * @throws InterruptedException
     */
    private void recRoutingMsg(MessageTask msgTask) throws IOException, InterruptedException {
        try {
            if (msgTask != null) {
                this.getChannel().exchangeDeclare(EXCHANGE_NAME, "direct");// 声明Exchange
                this.getChannel().queueDeclare(this.getQueueName(), this.isQuePersistence(), false,
                        false, this.getArgs());
                this.getChannel().basicQos(basicQos);// 告诉RabbitMQ同一时间给一个消息给消费者
                for (String routingKey : this.getRoutingKeys().split(",")) {
                    this.getChannel().queueBind(this.getQueueName(), EXCHANGE_NAME, routingKey);// 把Queue、Exchange及路由绑定
                }
                QueueingConsumer consumer = new QueueingConsumer(this.getChannel());
                this.getChannel().basicConsume(this.getQueueName(), this.isAutoAsk, consumer);
                while (true) {
                    try {
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        String message = new String(delivery.getBody(), this.getCharSet());
                        String routingKey = delivery.getEnvelope().getRoutingKey();
                        LOG.info("[recive][routingKey=" + routingKey + "]：" + message);
                        message = "routeKey=" + routingKey + "|" + message;
                        // 处理是否成功标识
                        boolean isSuccess = true;
                        try {
                            msgTask.handleTask(message);
                        } catch (Exception e) {
                            isSuccess = false;
                            LOG.error("rabbitmq handle task error:", e);
                        } finally {
                            if (!isAutoAsk) {
                                if (isSuccess) {
                                    this.getChannel().basicAck(
                                            delivery.getEnvelope().getDeliveryTag(), false);

                                } else {
                                    this.getChannel().basicNack(
                                            delivery.getEnvelope().getDeliveryTag(), false, true);
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("rabbitmq recive message queue error:", e);
                        Thread.sleep(1000);
                        this.destroy();
                        // 出现异常重新尝试处理，能解决rabbitmq服务器异常挂掉的情况
                        recRoutingMsg(msgTask);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("rabbitmq recive message server error:", e);
            throw e;
        } finally {
            this.destroy();
        }
    }

    /**
     * 从消息队列取出消息并进行处理
     * 
     * @param msgTask 任务处理类
     * @throws IOException
     * @throws InterruptedException
     */
    public void recMsg(MessageTask msgTask) throws IOException, InterruptedException {
        try {
            if (msgTask != null) {
                // 路由模式
                if (!isEmpty(this.getRoutingKeys()) && this.getRoutingKeys().split(",").length > 0) {
                    recRoutingMsg(msgTask);
                } else {
                    this.getChannel().queueDeclare(this.getQueueName(), this.isQuePersistence(),
                            false, false, this.getArgs());
                    this.getChannel().basicQos(basicQos);// 告诉RabbitMQ同一时间给一个消息给消费者
                    QueueingConsumer consumer = new QueueingConsumer(this.getChannel());
                    this.getChannel().basicConsume(this.getQueueName(), this.isAutoAsk, consumer);
                    while (true) {
                        try {
                            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                            String message = new String(delivery.getBody(), this.getCharSet());
                            LOG.info("[recive]：" + message);
                            // 处理是否成功标识
                            boolean isSuccess = true;
                            try {
                                msgTask.handleTask(message);
                            } catch (Exception e) {
                                isSuccess = false;
                                LOG.error("rabbitmq handle task error:", e);
                            } finally {
                                if (!isAutoAsk) {
                                    if (isSuccess) {
                                        this.getChannel().basicAck(
                                                delivery.getEnvelope().getDeliveryTag(), false);
                                    } else {
                                        this.getChannel().basicNack(
                                                delivery.getEnvelope().getDeliveryTag(), false,
                                                true);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            LOG.error("rabbitmq recive message queue error:", e);
                            Thread.sleep(1000);
                            this.destroy();
                            // 出现异常重新尝试处理，能解决rabbitmq服务器异常挂掉的情况
                            recMsg(msgTask);
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("rabbitmq recive message server error:", e);
            throw e;
        } finally {
            this.destroy();
        }
    }
}
