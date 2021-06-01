package com.example.test.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.entity.RespPage;
import com.example.test.entity.ResponseObj;
import com.example.test.entity.po.Customer;
import com.example.test.entity.req.CustomerReq;
import com.example.test.mapper.CustomerMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jie
 * @since 2021-05-20
 */
@Service
public class CustomerService extends ServiceImpl<CustomerMapper, Customer> {

    @Resource
    private CustomerMapper customerMapper;

    @DS("ttjb")
    @Cacheable(key = "'accountInfo:' + #customerReq.name", value = "customer", unless = "#result == null")
    public ResponseObj<RespPage<List<Customer>>> customerQuery(CustomerReq customerReq) {
        Page page = new Page(customerReq.getPageIndex(), customerReq.getPageSize(), true);
        Customer customer = new Customer();
        customer.setName(customerReq.getName());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>(customer);
        IPage<Customer> customerIPage = this.customerMapper.selectPage(page, queryWrapper);
        return ResponseObj.success(new RespPage(customerIPage.getRecords(), (int) customerIPage.getTotal()));
    }

    @CacheEvict(key = "'accountInfo:' + #customerReq.id", value = "customer")
    public ResponseObj<Boolean> customerUpdate(CustomerReq customerReq) {
        Customer customer = new Customer();
        customer.setName(customerReq.getName());
        customer.setId(customerReq.getId());
        customerMapper.updateById(customer);
        return ResponseObj.success(true);
    }

    public ResponseObj<?> queryList(List<String> ids) {
        return null;
    }
}
