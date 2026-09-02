package ru.wordle.wordservice.infrastructure.wordcards;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordcardsRequest {
    private String lang;
    private String length;
    private String includeLetters;
    private String excludeLetters;
    private String template;
    private boolean onlyUnique;
}