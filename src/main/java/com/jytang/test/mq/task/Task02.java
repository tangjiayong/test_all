package com.jytang.test.mq.task;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-28
 */
public class Task02 {

    public static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) {

        Channel channel = RabbitMqUtils.getChannel();

        try {
            //队列持久化
            boolean durable = true;
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.nextLine();
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                System.out.println("发出消息:" + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
