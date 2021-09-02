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
public class ReceiveLogs02 {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 声明一个交换机
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType());
        /**
         * 声明一个队列
         */
        String queueName = channel.queueDeclare().getQueue();
        /**
         * 交换机与队列进行绑定
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("ReceiveLogs02等待接受消息，把接受消息打印到控制台......");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogs02控制台接收到消息并打印:" + new String(message.getBody(), "UTF-8"));
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }
}
