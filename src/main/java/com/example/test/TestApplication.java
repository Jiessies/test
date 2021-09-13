package com.example.test;

import com.example.test.aspect.EnvironmentUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableFeignClients(basePackages = {"com.example.test.feignclient"})
@EnableDiscoveryClient
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SpringBootApplication
public class TestApplication {

    private static String env;

    public String getEnv() {
        return env;
    }

    @Value("${spring.profiles.active}")
    public void setEnv(String env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
        String env = EnvironmentUtils.searchByKey("spring.profiles.active");
        log.info("----------" + env);
    }

}
