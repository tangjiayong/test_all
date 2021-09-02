package com.jytang.test.mq.task;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 功能描述：手动确认
 *
 * @author jytang
 * @since 2021-08-28
 */
public class Task03 {

    public static final String TASK_QUEUE_NAME = "task_queue";

    public static final int COUNT = 1000;

    public static void main(String[] args) {

//        publishSingleConfirm();

//        publishBatchConfirm();

        publishAsyncConfirm();

    }

    /**
     * 异步确认
     */
    private static void publishAsyncConfirm() {
        Channel channel = RabbitMqUtils.getChannel();
        ConcurrentSkipListMap<Long, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        long begin = System.currentTimeMillis();
        try {
            //开启发布确认
            channel.confirmSelect();
            //队列持久化
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            /**
             * 消息确认成功函数回调
             */
            ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
                if (multiple) {
                    ConcurrentNavigableMap<Long, String> confirmed = concurrentSkipListMap.headMap(deliveryTag, true);
                    System.out.println("批量处理：" + confirmed);
                    confirmed.clear();

                } else {
                    System.out.println("单个处理");
                    concurrentSkipListMap.remove(deliveryTag);
                }

                System.out.println("确认消息:" + deliveryTag);
            };

            /**
             * 消息确认失败函数回调
             */
            ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
                System.out.println("未确认消息：" + deliveryTag);
            };
            //监听器
            channel.addConfirmListener(ackCallback, nackCallback);
            for (int i = 1; i <= COUNT; i++) {
                String message = String.valueOf(i);
                concurrentSkipListMap.put(channel.getNextPublishSeqNo(), message);
                //持久化消息
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            }
            long end = System.currentTimeMillis();

            System.out.println("异步确认耗时:" + (end - begin) + " ms");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量确认
     */
    private static void publishBatchConfirm() {

        Channel channel = RabbitMqUtils.getChannel();
        long begin = System.currentTimeMillis();
        try {
            //开启发布确认
            channel.confirmSelect();
            //队列持久化
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

            int limit = 100;
            for (int i = 1; i <= COUNT; i++) {
                String message = String.valueOf(i);
                //持久化消息
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                if (i % limit == 0) {
                    channel.waitForConfirms();
//                    boolean confirms = channel.waitForConfirms();
//                    if (confirms) {
//                        System.out.println("消息发送成功:" + message);
//                    }
                }
            }
            long end = System.currentTimeMillis();

            System.out.println("批量确认耗时:" + (end - begin) + " ms");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单独确认
     */
    private static void publishSingleConfirm() {

        Channel channel = RabbitMqUtils.getChannel();
        long begin = System.currentTimeMillis();
        try {
            //开启发布确认
            channel.confirmSelect();
            //队列持久化
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            for (int i = 0; i < COUNT; i++) {
                String message = String.valueOf(i);
                //持久化消息
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                channel.waitForConfirms();
            }
            long end = System.currentTimeMillis();

            System.out.println("单独确认耗时:" + (end - begin) + " ms");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
