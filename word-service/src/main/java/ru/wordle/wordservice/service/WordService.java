package ru.wordle.wordservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.wordle.wordservice.client.WordcardsClient;
import ru.wordle.wordservice.client.WordcardsRequest;
import ru.wordle.wordservice.client.WordcardsResponse;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordService {

    private final WordcardsClient wordcardsClient;
    private final Random random = new Random();

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
        log.info("Got random word: {}", word);
        return word.toLowerCase(new Locale("ru"));
    }

    public boolean isExists(String word) {
        log.info("Checking word existence: {}", word);
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