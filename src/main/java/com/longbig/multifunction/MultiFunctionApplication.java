package com.longbig.multifunction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.longbig.multifunction.mapper")
public class MultiFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiFunctionApplication.class, args);
    }

}
