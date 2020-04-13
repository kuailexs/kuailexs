package com.kuailexs.kuailexs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan({"com.kuailexs.mirror.ubports.web.mapper","com.kuailexs.kuailexs.mapper","com.kuailexs.blog.dao"})
@SpringBootApplication(scanBasePackages = {"com.kuailexs"})
@EnableTransactionManagement
public class KuailexsApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuailexsApplication.class, args);
    }

}
