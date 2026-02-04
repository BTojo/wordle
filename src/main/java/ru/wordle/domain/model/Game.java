package ru.wordle.domain.model;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class Game {

    static final int NUMBER_OF_ATTEMPTS = 5;
    public static final int NUMBER_OF_LETTERS = 5;

    private String gameId;
    private OffsetDateTime createdAt;
    private GameStatus gameStatus = GameStatus.PROCESS;
    private final String secretWord;
    private final List<Attempt> attemptsList = new ArrayList<>();

    public Game(String secretWord) {
        this.secretWord = secretWord;
        this.createdAt = OffsetDateTime.now();
        this.gameStatus = GameStatus.PROCESS;
    }

    public Attempt makeAttempt(String enterWord) {
        Attempt attempt = new Attempt();

        if (isMatched(enterWord)) {
            setGameStatus(GameStatus.WIN);
        }

        attempt.setLetters(check(enterWord));
        attemptsList.add(attempt);

        if (attemptsList.size() == NUMBER_OF_ATTEMPTS && gameStatus != GameStatus.WIN) {
            setGameStatus(GameStatus.LOSE);
        }

        return attempt;
    }

    private List<Letter> check(String enterWord) {
        List<Letter> letters = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            Letter letter = new Letter();
            char charIsSecretWord = secretWord.charAt(i);
            char charIsEnterWord = enterWord.charAt(i);

            if (isCharIsInItsPlace(charIsSecretWord, charIsEnterWord)) {
                letter.setValue(charIsSecretWord);
                letter.setStatus(LetterStatus.IN_PLACE);
            } else if (isCharBelongsWord(charIsEnterWord)) {
                letter.setValue(charIsEnterWord);
                letter.setStatus(LetterStatus.NOT_PLACE);
            } else {
                letter.setValue(charIsEnterWord);
                letter.setStatus(LetterStatus.MISSING);
            }
            letters.add(letter);
        }
        return letters;
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.PROCESS;
    }

    public boolean isWin() {
        return gameStatus == GameStatus.WIN;
    }

    public boolean isMatched(String enterWord) {
        return getSecretWord().equals(enterWord);
    }

    public boolean isCharIsInItsPlace(char secretWordChar, char enterWordChar) {
        return secretWordChar == enterWordChar;
    }

    public boolean isCharBelongsWord(char ch) {
        return getSecretWord().contains(String.valueOf(ch));
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + gameId + '\'' +
                ", status=" + gameStatus +
                ", secretWord='" + secretWord + '\'' +
                ", attempts=" + attemptsList.size() +
                '}';
    }
}
