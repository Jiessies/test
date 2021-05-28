package com.example.test.mq.listener;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoLocalTransactionChecker implements LocalTransactionChecker {
    @Override
    public TransactionStatus check(Message msg) {
        log.info("开始回查本地事务状态");
        //根据本地事务状态检查结果返回不同的TransactionStatus
        return TransactionStatus.CommitTransaction;
    }
}
