package be.nicholasmeyers.meyersai.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"be.nicholasmeyers.meyersai.chat.adpater"})
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
