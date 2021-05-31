package com.example.test.controller;

import com.example.test.entity.RespPage;
import com.example.test.entity.ResponseObj;
import com.example.test.entity.po.Customer;
import com.example.test.entity.req.CustomerReq;
import com.example.test.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jie
 * @since 2021-05-20
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("查询(vue)")
    @PostMapping("/query")
    public ResponseObj<RespPage<List<Customer>>> customerQuery(@Valid @RequestBody CustomerReq customerReq) {
        return customerService.customerQuery(customerReq);
    }

    @ApiOperation("查询(vue)")
    @PostMapping("/update")
    public ResponseObj<Boolean> customerUpdate(@Valid @RequestBody CustomerReq customerReq) {
        return customerService.customerUpdate(customerReq);
    }
}

