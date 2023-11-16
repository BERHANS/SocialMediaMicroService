package com.berhan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients//user servisine giden isteği auth attığı için buraya ekledik
public class AuthMicroServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthMicroServiceApplication.class);
    }
}