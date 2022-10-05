package com.elec5619;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.elec5619.dao")
public class EasyfeedbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyfeedbackApplication.class, args);
    }
}
