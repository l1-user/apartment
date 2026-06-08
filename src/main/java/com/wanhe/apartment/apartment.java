package com.wanhe.apartment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wanhe.apartment.mapper")
public class apartment {

    public static void main(String[] args) {
        SpringApplication.run(apartment.class, args);
    }

}

