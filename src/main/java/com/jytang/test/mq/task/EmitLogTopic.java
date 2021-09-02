package com.jytang.test.mq.task;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-31
 */
public class EmitLogTopic {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        /**
         * 声明一个交换机
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType());

        Map<String, String> bindingMap = new HashMap<>();
        bindingMap.put("quick.orange.rabbit", "Q1Q2都收到");
        bindingMap.put("quick.orange.fox", "Q1收到");


        for (Map.Entry<String, String> bindingKeyEntry : bindingMap.entrySet()) {
            String key = bindingKeyEntry.getKey();
            String value = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME, key, null, value.getBytes("UTF-8"));
            System.out.println("发送消息:" + value);
        }

    }
}
