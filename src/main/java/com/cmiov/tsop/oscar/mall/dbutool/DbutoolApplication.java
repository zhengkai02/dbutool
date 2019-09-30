package com.cmiov.tsop.oscar.mall.dbutool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DbutoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbutoolApplication.class, args);
    }

}
