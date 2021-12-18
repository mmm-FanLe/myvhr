package com.mayao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mayao.mapper")
public class Myvhr2Application {
    public static void main(String[] args) {
        SpringApplication.run(Myvhr2Application.class, args);
    }

}
