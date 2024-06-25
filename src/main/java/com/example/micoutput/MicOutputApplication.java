package com.example.micoutput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MicOutputApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MicOutputApplication.class, args);

        Menu menu = context.getBean(Menu.class);

        menu.entrance();

        context.close();
    }

}
