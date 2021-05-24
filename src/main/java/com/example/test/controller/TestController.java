package com.example.test.controller;

import com.example.test.entity.ResponseObj;
import com.example.test.entity.req.CustomerReq;
import com.example.test.feignclient.CustomerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("/query")
    public ResponseObj customerQuery(@RequestParam("name") String name) {
        CustomerReq customerReq = new CustomerReq();
        customerReq.setName(name);
        log.info("merberName is " + merberName + " merberAge is " + merberAge);
        return customerClient.customerQuery(customerReq);
    }
}
