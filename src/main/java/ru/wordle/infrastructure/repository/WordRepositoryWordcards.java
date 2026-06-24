package ru.wordle.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.wordle.domain.repository.WordRepository;
import ru.wordle.infrastructure.wordcards.WordcardsRequest;
import ru.wordle.infrastructure.wordcards.WordcardsResponse;

import java.util.*;

@Slf4j
@Repository
@Profile("wordcards")
public class WordRepositoryWordcards implements WordRepository {

    private static final String API_URL = "https://wordcards.ru/api/wordle";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Random random = new Random();

    @Override
    public String getRandomWord() {
        log.info("Fetching random Russian word from wordcards.ru");
        WordcardsRequest request = new WordcardsRequest(
                "ru", "5", "", "", "", false
        );

        ResponseEntity<WordcardsResponse> response = restTemplate.postForEntity(
                API_URL, request, WordcardsResponse.class
        );

        List<WordcardsResponse.WordItem> words = response.getBody() != null
                ? response.getBody().getPayload()
                : null;

        if (words == null || words.isEmpty()) {
            throw new RuntimeException("No words returned from wordcards.ru");
        }

        String word = words.get(random.nextInt(words.size())).getWordText();
        log.info("Got random word from wordcards.ru: {}", word);
        return word.toLowerCase(new Locale("ru"));
    }

    @Override
    public boolean isExists(String word) {
        log.info("Checking word existence in wordcards.ru: {}", word);
        WordcardsRequest request = new WordcardsRequest(
                "ru", "5", "", "", word, false
        );

        ResponseEntity<WordcardsResponse> response = restTemplate.postForEntity(
                API_URL, request, WordcardsResponse.class
        );

        List<WordcardsResponse.WordItem> words = response.getBody() != null
                ? response.getBody().getPayload()
                : null;

        boolean exists = words != null && !words.isEmpty();
        log.info("Word '{}' exists: {}", word, exists);
        return exists;
    }
}