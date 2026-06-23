package ru.wordle.infrastructure.wordcards;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WordcardsResponse {
    private List<String> words;
}