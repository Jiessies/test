package com.example.test.controller;

import com.example.test.configuration.mq.MqConfig;
import com.example.test.entity.ResponseObj;
import com.example.test.entity.req.CustomerReq;
import com.example.test.feignclient.CustomerClient;
import com.example.test.mq.MqService;
import com.example.test.utils.BlowUpJVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${merber.name:}")
    private String merberName;

    @Value("${merber.age:}")
    private Integer merberAge;

    @Resource
    private CustomerClient customerClient;

    @Resource
    private MqService mqService;

    @Resource
    private MqConfig mqConfig;

    @GetMapping("/query")
    public ResponseObj customerQuery(@RequestParam("name") String name) {
        CustomerReq customerReq = new CustomerReq();
        customerReq.setName(name);
        log.info("merberName is " + merberName + " merberAge is " + merberAge);
        return customerClient.customerQuery(customerReq);
    }

    @PostMapping("/sendMq/{delayTime}")
    public ResponseObj sendMq(@PathVariable("delayTime") Long delayTime, @RequestBody String message) {
        mqService.sendMsg(UUID.randomUUID().toString(), mqConfig.getTopic(), mqConfig.getTag(), message, false, delayTime);
        return ResponseObj.success();
    }

    @PostMapping("/sendOrderMq")
    public ResponseObj sendOrderMq(@RequestBody String message) {
        mqService.sendOrderMsg(UUID.randomUUID().toString(), mqConfig.getOrderTopic(), mqConfig.getOrderTag(), message, UUID.randomUUID().toString());
        return ResponseObj.success();
    }

    @PostMapping("/redis/{key}")
    public ResponseObj redisTest(@PathVariable("key") String key,
                                 @RequestBody String msg) {
//        redisTemplate.opsForList().leftPushAll(key,msg);
//        redisTemplate.opsForList().leftPop(key,20, TimeUnit.SECONDS);
        HashMap hashMap = new HashMap();
        hashMap.put(key, msg);
        redisTemplate.opsForHash().putAll(key + "_KEY", hashMap);
        return ResponseObj.success();
    }

    @GetMapping("/find")
    public ResponseObj test(@RequestParam("name") String name) {
        return ResponseObj.success(name);
    }

    @PostMapping("/oom")
    public ResponseObj testOOM(@RequestParam("type") int type) {
        String response = null;
        switch (type) {
            case 1:
                //?????????
                BlowUpJVM.testOutOfHeapMemory();
                response = "1";
                break;
            case 2:
                //?????????
                BlowUpJVM.testStackOverFlow();
                response = "2";
                break;
            case 3:
                //?????????
                BlowUpJVM.testPergemOutOfMemory2();
                response = "3";
                break;
            case 4:
                //????????????
                BlowUpJVM.testNativeMethodOutOfMemory();
                response = "4";
                break;

        }
        //-Xms64m -Xmx64m -XX:MetaspaceSize=32m -XX:MaxMetaspaceSize=64m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
        return ResponseObj.success(response);
    }

    public static void main(String[] args) {
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        threadLocal.set("zhangsan");
//        System.out.println(threadLocal.get());
//        threadLocal.remove();


//        final ThreadLocal threadLocal = new InheritableThreadLocal();
//        threadLocal.set("????????????");
//        Thread t = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                System.out.println("???????????? =" + threadLocal.get());
//            }
//        };
//        t.start();

        new Thread(() -> {
            System.out.println("asdfasdfasdfadsf");
        }).start();
        Runnable runnable = () -> {
            System.out.println("112312312");
        };
        new Thread(runnable).start();
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu", "xiaoming", "zhaoliu");

        list.stream()
                .map(value -> value + "1") //??????????????????Function???????????????
                .filter(value -> value.length() > 5) //??????????????????Predicate???????????????
                .forEach(value -> System.out.println(value));//??????????????????Consumer???????????????
    }

}
