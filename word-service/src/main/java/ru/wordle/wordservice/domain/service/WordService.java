package ru.wordle.wordservice.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wordle.wordservice.domain.repository.WordRepository;

@Service
@RequiredArgsConstructor
public class WordService {

    private final WordRepository wordRepository;

    public String getRandomWord() {
        return wordRepository.getRandomWord();
    }

    public boolean isExists(String word) {
        return wordRepository.isExists(word);
    }
}