package ru.wordle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WordleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordleApplication.class, args);
    }
}