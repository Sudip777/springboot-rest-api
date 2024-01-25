package com.sudip.restwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ResponseBody
public class RestWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWebServicesApplication.class, args);
    }

}
