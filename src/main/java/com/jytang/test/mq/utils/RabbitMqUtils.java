package com.jytang.test.mq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-08-27
 */
public class RabbitMqUtils {

    public static Channel getChannel(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPassword("guest");
        factory.setPassword("guest");
        Connection connection;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return channel;
    }
}
