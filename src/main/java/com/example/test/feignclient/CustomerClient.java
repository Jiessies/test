package com.example.test.feignclient;

import com.example.test.entity.ResponseObj;
import com.example.test.entity.req.CustomerReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient("${feign.recycl-rabbish-service.name}")
public interface CustomerClient {

    @PostMapping(value = "/recycl/api//admin/customer/query", consumes = "application/json;charset=UTF-8")
    ResponseObj customerQuery(@Valid @RequestBody CustomerReq customerReq);
}
