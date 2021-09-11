package com.jytang.rabbitmqDemo.config;

import com.jytang.rabbitmqDemo.constant.RabbitMqConstant;
import com.rabbitmq.client.BuiltinExchangeType;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：使用官方自带的延迟插件: x-delayed_exchange
 *
 * @author jytang
 * @since 2021-09-05
 */
@Configuration
public class DelayedQueueConfig {

    /**
     * 声明队列
     *
     * @return
     */
    @Bean
    public Queue delayedQueue() {
        return new Queue(RabbitMqConstant.DELAYED_QUEUE_NAME);
    }

    /**
     * 使用插件，返回用户自定义交换机
     *
     * @return
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", BuiltinExchangeType.DIRECT.getType());
        return new CustomExchange(RabbitMqConstant.DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    /**
     * 绑定
     *
     * @param queue
     * @param delayedExchange
     * @return
     */
    @Bean
    public Binding bingDelayedQueue(@Qualifier("delayedQueue") Queue queue, @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(RabbitMqConstant.DELAYED_ROUTING_KEY).noargs();
    }
}
