package com.jytang.test.mq.task;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-27
 */
public class Task01 {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {

        Channel channel = RabbitMqUtils.getChannel();
        try {
            /**
             * 1、队列名称
             * 2、是否持久化
             * 3、是否排他只供一个消费者消费，true是排他
             * 4、自动删除，当队列最后一个消费者断开连接，true队列自动删除
             * 5、其他参数
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                /**
                 * 1、交换机
                 * 2、路由key
                 * 3、其他参数
                 * 4、消息
                 */
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("消息发送完成:" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
