package com.jytang.test.mq.work;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-31
 */
public class ReceiveTopicLogs02 {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static final String QUEUE_NAME = "Q2";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 声明一个交换机
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType());
        /**
         * 声明一个队列
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**
         * 交换机与队列进行绑定
         */
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "lazy.#");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.*.rabbit");

        System.out.println("ReceiveTopicLogs02 等待接受消息，把接受消息打印到控制台......");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveTopicLogs02 控制台接收到消息并打印:" + new String(message.getBody(), "UTF-8"));
            System.out.println("接受队列：" + QUEUE_NAME + ",绑定键:" + message.getEnvelope().getRoutingKey());
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }
}
