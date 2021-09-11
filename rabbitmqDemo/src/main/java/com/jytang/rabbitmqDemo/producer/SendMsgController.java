package com.jytang.rabbitmqDemo.producer;

import com.jytang.rabbitmqDemo.constant.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-09-05
 */
@RestController
@RequestMapping("ttl")
@Slf4j
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * ttl在队列声明时固定
     *
     * @param message
     */
    @RequestMapping("sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间:{},发送一条消息给两个ttl队列:{}", new Date(), message);

        rabbitTemplate.convertAndSend(RabbitMqConstant.X_EXCHANGE, "XA", "消息来自ttl 为10s的队列:" + message);
        rabbitTemplate.convertAndSend(RabbitMqConstant.X_EXCHANGE, "XB", "消息来自ttl 为40s的队列:" + message);
    }

    /**
     * 发送一条自定义ttl的队列
     * 由于mq只检查第一个消息是否过期，如果过期会丢弃到死信队列中。
     * 如果第一个消息的延时时长比较长，第二个消息延时时长很短，此时就会出现第二个消息不能按时死亡的现象
     *
     * @param msg
     * @param ttlTime
     */
    @RequestMapping("sendMsgWithTTL/{msg}/{ttlTime}")
    public void sendMsgWithTTL(@PathVariable String msg, @PathVariable String ttlTime) {

        rabbitTemplate.convertAndSend(RabbitMqConstant.X_EXCHANGE, "XC", msg, message -> {
            message.getMessageProperties().setExpiration(ttlTime);
            return message;
        });

        log.info("当前时间:{},发送一条消息给ttl:{} ms 的队列:{}", new Date(), ttlTime, msg);

    }

    /**
     * 使用插件发送延迟消息
     *
     * @param msg
     * @param ttlTime
     */
    @RequestMapping("sendMsgWithPlugins/{msg}/{ttlTime}")
    public void sendMsgWithPlugins(@PathVariable String msg, @PathVariable Integer ttlTime) {
        rabbitTemplate.convertAndSend(RabbitMqConstant.DELAYED_EXCHANGE_NAME, RabbitMqConstant.DELAYED_ROUTING_KEY, ttlTime + "_" + msg, message -> {
            message.getMessageProperties().setDelay(ttlTime);
            return message;
        });

        log.info("当前时间:{},发送一条消息给ttl:{} ms 的队列:{}", new Date(), ttlTime, ttlTime + "_" + msg);
    }
}
