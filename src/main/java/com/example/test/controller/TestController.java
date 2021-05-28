package com.example.test.controller;

import com.example.test.configuration.mq.MqConfig;
import com.example.test.entity.ResponseObj;
import com.example.test.entity.req.CustomerReq;
import com.example.test.feignclient.CustomerClient;
import com.example.test.mq.MqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {
    @Value("${merber.name:}")
    private String merberName;

    @Value("${merber.age:}")
    private Integer merberAge;

    @Resource
    private CustomerClient customerClient;

    @Resource
    private MqService mqService;

    @Resource
    private MqConfig mqConfig;

    @GetMapping("/query")
    public ResponseObj customerQuery(@RequestParam("name") String name) {
        CustomerReq customerReq = new CustomerReq();
        customerReq.setName(name);
        log.info("merberName is " + merberName + " merberAge is " + merberAge);
        return customerClient.customerQuery(customerReq);
    }

    @PostMapping("/sendMq/{delayTime}")
    public ResponseObj sendMq(@PathVariable("delayTime") Long delayTime, @RequestBody String message) {
        mqService.sendMsg(UUID.randomUUID().toString(), mqConfig.getTopic(), mqConfig.getTag(), message, false, delayTime);
        return ResponseObj.success();
    }

    @PostMapping("/sendOrderMq")
    public ResponseObj sendOrderMq(@RequestBody String message) {
        mqService.sendOrderMsg(UUID.randomUUID().toString(), mqConfig.getOrderTopic(), mqConfig.getOrderTag(), message, UUID.randomUUID().toString());
        return ResponseObj.success();
    }
}
