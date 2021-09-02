package com.jytang.test.mq.task;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-09-02
 */
public class Producer {


    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 声明一个交换机
         */
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);

//        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 1; i <= 10; i++) {
            String message = "info" + i;

//            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", basicProperties, message.getBytes());
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes());

            System.out.println("Producer 生产者发送消息：" + message);
        }


    }
}
