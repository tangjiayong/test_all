package com.jytang.rabbitmqDemo.producer;

import com.jytang.rabbitmqDemo.constant.RabbitMqConstant;
import com.jytang.rabbitmqDemo.utils.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 功能描述：发布确认
 *
 * @author jytang
 * @since 2021-09-05
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyCallBack myCallBack;

    /**
     * 依赖注入rabbitmqTemplate之后在设置回调对象
     */
    @PostConstruct
    public void init() {
      rabbitTemplate.setConfirmCallback(myCallBack);
      rabbitTemplate.setMandatory(true);
      rabbitTemplate.setReturnsCallback(myCallBack);
    }

    @RequestMapping("sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间:{},发送一条消息给队列:{}", new Date(), message);
        CorrelationData correlationData1 = new CorrelationData("1");
        String routingKey = RabbitMqConstant.CONFIRM_ROUTING_KEY;
        rabbitTemplate.convertAndSend(RabbitMqConstant.CONFIRM_EXCHANGE_NAME, routingKey, message + routingKey, correlationData1);
        log.info("发送消息1:{}", message + routingKey);
        CorrelationData correlationData2 = new CorrelationData("2");
        routingKey = "key2";
        rabbitTemplate.convertAndSend(RabbitMqConstant.CONFIRM_EXCHANGE_NAME, routingKey, message + routingKey, correlationData2);
        log.info("发送消息2:{}", message + routingKey);
    }
}
