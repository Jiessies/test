package com.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.entity.po.Customer;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jie
 * @since 2021-05-20
 */
@Repository
public interface CustomerMapper extends BaseMapper<Customer> {

}
