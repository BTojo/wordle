package ru.wordle.infrastructure.wordservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "word-service",
        url = "${feign.client.config.word-service.url}"
)
public interface WordServiceClient {

    @GetMapping("/api/words/random")
    RandomWordResponseDto getRandomWord();

    @GetMapping("/api/words/exists")
    WordExistsResponseDto checkExists(@RequestParam("word") String word);
}