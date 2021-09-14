package com.example.test.aspect;

import com.alibaba.fastjson.JSON;
import com.example.test.entity.AnalysisLog;
import com.example.test.entity.ResponseObj;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
@Component
public class LogRequestAspect {

    @Pointcut("execution(* com.example.test.controller..*(..))")
    public void verification() {
    }

    @Around("verification()")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Stopwatch stopWatch = Stopwatch.createStarted();
        Object responseObj = null;
        Object objectRequest = null;
        String authorization = null;
        String requestURI = null;
        Long appCustomerId = null;
        String adminCustomerId = null;
        try {
//            appCustomerId = TokenHolder.getLongId();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        try {
//            adminCustomerId = TokenHolder.getId();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        try {
            Object[] objects = joinPoint.getArgs();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            requestURI = request.getRequestURI();
            authorization = request.getHeader("Authorization");
            if (request.getMethod().equals("POST")) {
                if (objects.length > 0) {
                    objectRequest = objects[0];
                }
            } else if (request.getMethod().equals("GET")) {
                objectRequest = showParams(request);
            } else {
                return ResponseObj.fail("暂不支持" + request.getMethod() + "请求方式!");
            }
            responseObj = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.error("recordLog error is: {}", throwable.toString());
            responseObj = ResponseObj.fail(throwable.getMessage());
            throw throwable;
        } finally {
            Map headerMap = new HashMap();
            headerMap.put("Authorization", authorization);
            AnalysisLog logEntity = AnalysisLog.builder()
                    .requestHeader(headerMap)
                    .requestContent(objectRequest)
                    .responseContent(responseObj)
                    .requestURI(requestURI)
                    .adminCustomerId(adminCustomerId)
                    .appCustomerId(appCustomerId)
                    .runtime(stopWatch.stop().elapsed(TimeUnit.MILLISECONDS))
                    .build();
            log.info("@Aspect log is: " + JSON.toJSONString(logEntity));
        }
        return responseObj;
    }

    private Map showParams(HttpServletRequest request) {
        Map map = new HashMap();
        try {
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length == 1) {
                    String paramValue = paramValues[0];
                    if (paramValue.length() != 0) {
                        map.put(paramName, paramValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
