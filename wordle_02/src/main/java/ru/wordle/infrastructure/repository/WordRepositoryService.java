package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.repository.WordRepository;
import ru.wordle.infrastructure.wordservice.WordServiceClient;

@Slf4j
@Repository
@Profile("word-service")
@RequiredArgsConstructor
public class WordRepositoryService implements WordRepository {

    private final WordServiceClient wordServiceClient;

    @Override
    public String getRandomWord() {
        log.info("Fetching random word from word-service");
        String word = wordServiceClient.getRandomWord().getWord();
        log.info("Got random word from word-service: {}", word);
        return word;
    }

    @Override
    public boolean isExists(String word) {
        log.info("Checking word existence via word-service: {}", word);
        boolean exists = wordServiceClient.checkExists(word).isExists();
        log.info("Word '{}' exists: {}", word, exists);
        return exists;
    }
}