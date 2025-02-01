package org.server.onlinelearningserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class OnlineLearningServerApplication {

    public static void main(String[] args) {
        System.out.println("Hello From Server : " + new Date());
        SpringApplication.run(OnlineLearningServerApplication.class, args);
    }

}
