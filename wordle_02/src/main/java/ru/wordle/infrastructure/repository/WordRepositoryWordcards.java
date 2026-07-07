package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.repository.WordRepository;
import ru.wordle.infrastructure.wordcards.WordcardsClient;
import ru.wordle.infrastructure.wordcards.WordcardsRequest;
import ru.wordle.infrastructure.wordcards.WordcardsResponse;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Slf4j
@Repository
@Profile("wordcards")
@RequiredArgsConstructor
public class WordRepositoryWordcards implements WordRepository {

    private final WordcardsClient wordcardsClient;
    private final Random random = new Random();

    @Override
    public String getRandomWord() {
        log.info("Fetching random Russian word from wordcards.ru");
        WordcardsRequest request = new WordcardsRequest(
                "ru", "5", "", "", "", false
        );

        WordcardsResponse response = wordcardsClient.getWords(request);
        List<WordcardsResponse.WordItem> words = response.getPayload();

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

        WordcardsResponse response = wordcardsClient.getWords(request);
        List<WordcardsResponse.WordItem> words = response.getPayload();

        boolean exists = words != null && !words.isEmpty();
        log.info("Word '{}' exists: {}", word, exists);
        return exists;
    }
}