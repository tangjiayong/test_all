package com.jytang.test.mq.work;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.jytang.test.mq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * 功能描述：手动应答
 *
 * @author jytang
 * @since 2021-08-28
 */
public class Work02_1 {

    public static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) {
        Channel channel = RabbitMqUtils.getChannel();
        try {
            //0：轮询分发，1：不公平分发 其他：预期值
            channel.basicQos(5);
            System.out.println("C1线程处理较慢");
            DeliverCallback deliverCallback = (consumerTag, message) -> {
//                SleepUtils.sleep(1);
                System.out.println("接收到消息:" + new String(message.getBody(), "UTF-8"));
                /**
                 * 手动应答
                 * 1、消息的标记Tag
                 * 2、是否批量应答信道中的消息， true 批量 false 不批量，最好的选择
                 */
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };

            CancelCallback cancelCallback = consumerTag -> {
                System.out.println(consumerTag + "消息被消费者接口取消回调逻辑");
            };

            boolean autoAck = false;

            channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
