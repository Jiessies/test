package com.example.test.configuration.mq.order;

import com.aliyun.openservices.ons.api.bean.OrderProducerBean;
import com.example.test.configuration.mq.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderProducerClient {
    @Autowired
    private MqConfig mqConfig;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public OrderProducerBean buildOrderProducer() {
        OrderProducerBean orderProducerBean = new OrderProducerBean();
        orderProducerBean.setProperties(mqConfig.getMqPropertie());
        return orderProducerBean;
    }

}
