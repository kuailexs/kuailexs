package com.kuailexs.mirror.ubports.web;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.kuailexs.mirror.ubports.web.mapper"})
@SpringBootApplication(scanBasePackages = {"com.kuailexs.mirror.ubports.web"})
public class MirrorUbportsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MirrorUbportsWebApplication.class, args);
    }

}
