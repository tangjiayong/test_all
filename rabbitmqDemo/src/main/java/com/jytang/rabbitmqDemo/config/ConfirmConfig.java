package com.jytang.rabbitmqDemo.config;

import com.jytang.rabbitmqDemo.constant.RabbitMqConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：发布确认
 *
 * @author jytang
 * @since 2021-09-05
 */
@Configuration
public class ConfirmConfig {

    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(RabbitMqConstant.BACK_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange confirmExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(RabbitMqConstant.CONFIRM_EXCHANGE_NAME)
                .durable(true)
                //设置改交换机的备份交换机
                .withArgument("alternate-exchange", RabbitMqConstant.BACK_EXCHANGE_NAME);
        return exchangeBuilder.build();
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.durable(RabbitMqConstant.CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding bindingQueueExchange(@Qualifier("confirmQueue") Queue queue, @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(queue).to(confirmExchange).with(RabbitMqConstant.CONFIRM_ROUTING_KEY);
    }


    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(RabbitMqConstant.BACKUP_QUEUE_NAME).build();
    }

    @Bean
    public Queue warningQueue() {
        return new Queue(RabbitMqConstant.WARNING_QUEUE_NAME);
    }

    @Bean
    public Binding backupQueueExchange(@Qualifier("backupQueue") Queue queue, @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(queue).to(backupExchange);
    }

    @Bean
    public Binding warningQueueExchange(@Qualifier("warningQueue") Queue queue, @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(queue).to(backupExchange);
    }
}
