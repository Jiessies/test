package com.example.test.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.test.entity.RespPage;
import com.example.test.entity.ResponseObj;
import com.example.test.entity.po.Customer;
import com.example.test.entity.po.TblDashboardCopyIndex;
import com.example.test.entity.req.CustomerReq;
import com.example.test.entity.req.DashboardCopyIndexReq;
import com.example.test.mapper.CustomerMapper;
import com.example.test.mapper.TblDashboardCopyIndexMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private TblDashboardCopyIndexMapper copyIndexMapper;

    @DS("jie2")
    @Cacheable(key = "'accountInfo:' + #customerReq.name", value = "customer", unless = "#result == null")
    public ResponseObj<RespPage<List<Customer>>> customerQuery(CustomerReq customerReq) {
        Page page = new Page(customerReq.getPageIndex(), customerReq.getPageSize(), true);
        Customer customer = new Customer();
        customer.setName(customerReq.getName());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>(customer);
        IPage<Customer> customerIPage = this.customerMapper.selectPage(page, queryWrapper);
        return ResponseObj.success(new RespPage(customerIPage.getRecords(), (int) customerIPage.getTotal()));
    }

    @CacheEvict(key = "'accountInfo:' + #customerReq.name", value = "customer")
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

    @Transactional
    public ResponseObj<Boolean> addDb(DashboardCopyIndexReq request) {
        TblDashboardCopyIndex copyIndex = new TblDashboardCopyIndex();
        BeanUtils.copyProperties(request, copyIndex);
        copyIndexMapper.insert(copyIndex);
        CustomerReq customerReq = new CustomerReq();
        customerReq.setName(String.valueOf(request.getDbId()));
//        addCustomer(customerReq);

        ((CustomerService) AopContext.currentProxy()).addCustomer(customerReq);

        if (request != null) {
            throw new RuntimeException();
        }
        return ResponseObj.success(true);
    }

    @Transactional
    public ResponseObj<Boolean> addCustomer(CustomerReq request) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(request, customer);
        customerMapper.insert(customer);
        if (request != null) {
            throw new RuntimeException();
        }
        return ResponseObj.success(true);
    }
}
