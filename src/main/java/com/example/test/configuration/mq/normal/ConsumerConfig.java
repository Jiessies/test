package com.example.test.configuration.mq.normal;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.example.test.configuration.mq.MqConfig;
import com.example.test.entity.MqContents;
import com.example.test.mq.listener.OneMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ConsumerConfig {

    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private OneMessageListener oneMessageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = mqConfig.getMqPropertie();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        // 将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        consumerBean.setProperties(properties);
        // 订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();

        Subscription subscription = new Subscription();
        subscription.setTopic(mqConfig.getTopic());
        subscription.setExpression(MqContents.MQ_SHOP_RECYCLE_EXCELLENT_TAG);
        subscriptionTable.put(subscription, oneMessageListener);

        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }

}
