package com.example.test.entity.req;

import com.example.test.entity.PageRequest;
import lombok.Data;

@Data
public class CustomerReq extends PageRequest {
    private Long id;
    private String name;
    private String phone;
}
