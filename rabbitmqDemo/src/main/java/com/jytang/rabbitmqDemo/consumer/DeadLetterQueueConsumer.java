package com.jytang.rabbitmqDemo.consumer;

import com.jytang.rabbitmqDemo.constant.RabbitMqConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-09-05
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间:{}，收到死信队列的消息为:{}", new Date(), msg);

    }

    @RabbitListener(queues = RabbitMqConstant.DELAYED_QUEUE_NAME)
    public void receiveDelayed(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间:{}，收到延迟队列的消息为:{}", new Date(), msg);

    }
}
