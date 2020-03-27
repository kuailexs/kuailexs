package com.kuailexs.kuailexs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan({"com.kuailexs.mirror.ubports.web.mapper","com.kuailexs.kuailexs.mapper"})
@SpringBootApplication(scanBasePackages = {"com.kuailexs"})
public class KuailexsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuailexsApplication.class, args);
    }

}
