package com.jytang.test.mq.work;

import com.jytang.test.mq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：死信队列消费者
 *
 * @author jytang
 * @since 2021-09-02
 */
public class DeadLetterConsumer1 {

    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static final String DEAD_EXCHANGE = "dead_exchange";

    public static final String NORMAL_QUEUE = "normal_queue";

    public static final String DEAD_QUEUE = "dead_queue";


    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        //交换机设置为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT.getType());
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT.getType());
        //死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        //正常队列
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "lisi");
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");

        System.out.println("等待接受消息......");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            if (StringUtils.equals("info5", msg)) {
                System.out.println("DeadLetterConsumer1 拒绝控制台接收到消息并打印:" + msg + " 消息被拒绝。");
                // false 不放回原队列
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("DeadLetterConsumer1 控制台接收到消息并打印:" + msg);
                //false 批量应答
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }

        };

        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, consumerTag -> {
        });

    }
}
