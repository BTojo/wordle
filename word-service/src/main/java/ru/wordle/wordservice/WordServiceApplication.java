package ru.wordle.wordservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WordServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WordServiceApplication.class, args);
    }
}