package com.example.test.mq.listener;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OneMessageListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext context) {

        log.info("------------1" + JSON.toJSONString(message));
        return Action.CommitMessage;
    }
}
