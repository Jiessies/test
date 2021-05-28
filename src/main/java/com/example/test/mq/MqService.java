package com.example.test.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqService {

    @Autowired
    private ProducerBean producerBean;

    @Autowired
    private OrderProducerBean orderProducerBean;

    /**
     * @param msgKey
     * @param topic
     * @param tag
     * @param message
     * @param isAsync
     * @param delayTime
     */
    public void sendMsg(String msgKey, String topic, String tag, String message, Boolean isAsync, Long delayTime) {
        log.info(">>>sendMsg send msg is: {}", message);
        Message msg = new Message(topic, tag, message.getBytes());
        //设置代表消息的业务关键属性，请尽可能全局唯一。以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发。注意：不设置也不会影响消息正常收发。
        msg.setKey(msgKey);
        if (delayTime != null && delayTime != 0) {
            // 延时消息，单位毫秒（ms），在指定延迟时间（当前时间之后）进行投递，例如消息在3秒后投递。设置消息需要被投递的时间。
            msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
        }
        if (isAsync) {
            producerBean.sendAsync(msg, new SendCallback() {
                @Override
                public void onSuccess(final SendResult sendResult) {
                    log.info("onSuccess topic is {}, tag is {}, messageId is {} message is {}", sendResult.getTopic(), tag, sendResult.getMessageId(), message);
                }

                @Override
                public void onException(OnExceptionContext context) {
                    log.info("onException topic is {}, tag is {}, messageId is {} message is {}", context.getTopic(), tag, context.getMessageId(), message);
                }
            });
        } else {
            //同步发送消息，只要不抛异常就是成功。
            SendResult sendResult = producerBean.send(msg);
            if (sendResult != null) {
                log.info("Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
            }
        }
    }

    /**
     * @param msgKey
     * @param topic
     * @param tag
     * @param message
     * @param shardingKey
     */
    public void sendOrderMsg(String msgKey, String topic, String tag, String message, String shardingKey) {
        log.info(">>>sendOrderMsg send msg is: {}", message);
        Message msg = new Message(topic, tag, message.getBytes());
        //设置代表消息的业务关键属性，请尽可能全局唯一。以方便您在无法正常收到消息情况下，可通过控制台查询消息并补发。注意：不设置也不会影响消息正常收发。
        msg.setKey(msgKey);
        //同步发送消息，只要不抛异常就是成功。
        SendResult sendResult = orderProducerBean.send(msg, shardingKey);
        if (sendResult != null) {
            log.info("Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
        }
    }

}
