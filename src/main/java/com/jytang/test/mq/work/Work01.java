package com.jytang.test.mq.work;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * 功能描述：自动应答
 *
 * @author jytang
 * @since 2021-08-27
 */
public class Work01 {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {

        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息：" + new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag + "消息被消费者接口取消回调逻辑");
        };

        try {
            /**
             * 1、队列名
             * 2、自动应答
             * 3、消费者成功消费的回调
             * 4、消费者取消的回调
             */
            System.out.println("C2 等待接受消息");
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
