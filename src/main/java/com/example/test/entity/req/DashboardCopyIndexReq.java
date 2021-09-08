package com.example.test.entity.req;

import com.example.test.entity.PageRequest;
import lombok.Data;

@Data
public class DashboardCopyIndexReq extends PageRequest {
    private Long parentId;
    private Long dbId;
    private Long sourceDbId;
}
