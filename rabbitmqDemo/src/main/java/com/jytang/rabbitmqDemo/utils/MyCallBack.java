package com.jytang.rabbitmqDemo.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 功能描述：
 *
 * @author jytang
 * @since 2021-09-06
 */

@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    /**
     * 交换机不管是否收到消息都会回调的方法
     *
     * @param correlationData 消息相关数据
     * @param ack 交换机是否收到消息
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        log.info("confirm input={},{},{}", JSONObject.toJSONString(correlationData), ack, cause);
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机收到id={}的消息", id);
        } else {
            log.info("交换机没收到id={}的消息，原因:{}", id, cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息:{},被交换机:{} 退回，退回原因:{},路由:{}", new String(returned.getMessage().getBody()), returned.getExchange(), returned.getReplyText(),
                returned.getRoutingKey());
    }
}
