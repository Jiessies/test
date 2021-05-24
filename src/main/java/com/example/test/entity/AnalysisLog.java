package com.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisLog {
    private Object requestHeader;
    private Object requestContent;
    private Object responseContent;
    private Long appCustomerId;
    private String adminCustomerId;
    private String requestURI;
    private long runtime;
}
