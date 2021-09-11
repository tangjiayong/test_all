package com.jytang.rabbitmqDemo.config;

import com.jytang.rabbitmqDemo.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.jytang.rabbitmqDemo.constant.RabbitMqConstant.Y_DEAD_LETTER_EXCHANGE;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-09-05
 */
@Configuration
public class TtlQueueConfig {


    /**
     * 声明交换机
     *
     * @return
     */
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(RabbitMqConstant.X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(RabbitMqConstant.Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明队列
     *
     * @return
     */
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        arguments.put("x-message-ttl", 10000);
        return QueueBuilder.durable(RabbitMqConstant.QUEUE_A).withArguments(arguments).build();
    }

    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        arguments.put("x-message-ttl", 40000);
        return QueueBuilder.durable(RabbitMqConstant.QUEUE_B).withArguments(arguments).build();
    }

    /**
     * 自定义ttl
     *
     * @return
     */
    @Bean("queueC")
    public Queue queueC() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(RabbitMqConstant.QUEUE_C).withArguments(arguments).build();
    }

    /**
     * 队列绑定交换机
     *
     * @param queueA
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueBindingXA(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBindingXB(@Qualifier("queueB") Queue queueB, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueBindingXC(@Qualifier("queueC") Queue queueC, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean("queueD")
    public Queue queueD() {
        return new Queue(RabbitMqConstant.DEAD_LETTER_QUEUE);
    }

    @Bean
    public Binding queueBindingYD(@Qualifier("queueD") Queue queueD, @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

}
