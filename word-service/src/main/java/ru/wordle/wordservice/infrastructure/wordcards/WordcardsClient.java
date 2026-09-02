package ru.wordle.wordservice.infrastructure.wordcards;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "wordcards",
        url = "${feign.client.config.wordcards.url}",
        path = "${feign.client.config.wordcards.api-base-url}"
)
public interface WordcardsClient {

    @PostMapping("/wordle")
    WordcardsResponseDto getWords(@RequestBody WordcardsRequest request);
}
