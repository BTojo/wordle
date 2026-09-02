package ru.wordle.wordservice.infrastructure.repository;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.wordle.wordservice.domain.repository.WordRepository;
import ru.wordle.wordservice.infrastructure.wordcards.WordcardsClient;
import ru.wordle.wordservice.infrastructure.wordcards.WordcardsRequest;
import ru.wordle.wordservice.infrastructure.wordcards.WordcardsResponseDto;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WordRepositoryWordcards implements WordRepository {

    private final WordcardsClient wordcardsClient;
    private final Random random = new Random();

    @Override
    public String getRandomWord() {
        log.info("Fetching random Russian word from wordcards.ru");
        WordcardsRequest request = new WordcardsRequest("ru", "5", "", "", "", false);
        WordcardsResponseDto response = wordcardsClient.getWords(request);
        List<WordcardsResponseDto.WordItem> words = response.getPayload();

        if (words == null || words.isEmpty()) {
            throw new IllegalStateException("No words returned from wordcards.ru");
        }

        String word = words.get(random.nextInt(words.size())).getWordText();
        log.info("Got random word: {}", word);
        return word.toLowerCase(new Locale("ru"));
    }

    @Override
    public boolean isExists(String word) {
        log.info("Checking word existence: {}", word);
        WordcardsRequest request = new WordcardsRequest("ru", "5", "", "", word, false);
        WordcardsResponseDto response = wordcardsClient.getWords(request);
        List<WordcardsResponseDto.WordItem> words = response.getPayload();
        boolean exists = words != null && !words.isEmpty();
        log.info("Word '{}' exists: {}", word, exists);
        return exists;
    }
}
