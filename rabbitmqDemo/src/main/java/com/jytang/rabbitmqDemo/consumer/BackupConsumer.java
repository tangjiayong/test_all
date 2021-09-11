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
 * 功能描述：备份消费者
 *
 * @author jytang
 * @since 2021-09-07
 */
@Slf4j
@Component
public class BackupConsumer {

    @RabbitListener(queues = RabbitMqConstant.BACKUP_QUEUE_NAME)
    public void receiveBackup(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间:{}，备份不可路由消息:{}", new Date(), msg);

    }
}
