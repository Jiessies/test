package com.example.test.configuration.mq.transaction;

import com.aliyun.openservices.ons.api.bean.TransactionProducerBean;
import com.example.test.configuration.mq.MqConfig;
import com.example.test.mq.listener.DemoLocalTransactionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionProducerClient {

    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private DemoLocalTransactionChecker localTransactionChecker;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public TransactionProducerBean buildTransactionProducer() {
        TransactionProducerBean producer = new TransactionProducerBean();
        producer.setProperties(mqConfig.getMqPropertie());
        producer.setLocalTransactionChecker(localTransactionChecker);
        return producer;
    }
}
