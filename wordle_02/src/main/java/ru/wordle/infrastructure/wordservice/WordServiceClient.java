package ru.wordle.infrastructure.wordservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "word-service",
        url = "${feign.client.config.word-service.url}",
        path = "${feign.client.config.word-service.path}"
)
public interface WordServiceClient {

    @GetMapping("/random")
    RandomWordResponseDto getRandomWord();

    @GetMapping("/exists")
    WordExistsResponseDto checkExists(@RequestParam("word") String word);
}
