package com.jytang.test.mq.work;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 功能描述：死信队列消费者
 *
 * @author jytang
 * @since 2021-09-02
 */
public class DeadLetterConsumer2 {


    public static final String DEAD_EXCHANGE = "dead_exchange";


    public static final String DEAD_QUEUE = "dead_queue";


    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        //交换机设置为direct
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT.getType());
        //死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        System.out.println("等待接受死信消息......");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("DeadLetterConsumer2 控制台接收到消息并打印:" + new String(message.getBody(), "UTF-8"));
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {
        });

    }
}
