package ru.wordle.wordservice.client;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WordcardsResponse {

    private List<WordItem> payload;
    private String message;

    @Getter
    @Setter
    public static class WordItem {
        private String wordText;
        private String translation;
        private String rejectReason;
    }
}