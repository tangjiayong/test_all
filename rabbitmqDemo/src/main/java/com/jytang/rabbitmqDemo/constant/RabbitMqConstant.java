package com.jytang.rabbitmqDemo.constant;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-09-05
 */
public class RabbitMqConstant {
    /**
     * 死信队列
     */
    public static final String X_EXCHANGE = "X";
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String DEAD_LETTER_QUEUE = "QD";
    /**
     * 延迟队列
     */
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingKey";

    /**
     * 发布确认
     */
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    public static final String CONFIRM_ROUTING_KEY = "key1";
    /**
     * 备份
     */
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";
    public static final String BACK_EXCHANGE_NAME = "backup.exchange";
}
