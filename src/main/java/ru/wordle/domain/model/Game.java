package ru.wordle.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.wordle.domain.exception.GameAlreadyFinishedException;
import ru.wordle.domain.exception.NoAttemptsLeftException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter

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
        if (!isInProgress()) {
            throw new GameAlreadyFinishedException("Game finished");
        }

        if (attemptsList.size() >= NUMBER_OF_ATTEMPTS) {
            throw new NoAttemptsLeftException("No attempts left");
        }

        Attempt attempt = new Attempt();


        attempt.setGameId(this.gameId);
        attempt.setAttemptNumber(attemptsList.size() + 1);
        attempt.setLetters(check(enterWord));

        attemptsList.add(attempt);

        if (isMatched(enterWord)) {
            setGameStatus(GameStatus.WIN);
        } else if (attemptsList.size() == NUMBER_OF_ATTEMPTS) {
            setGameStatus(GameStatus.LOSE);
        }

        return attempt;
    }

    private List<Letter> check(String enterWord) {
        List<Letter> letters = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            Letter letter = new Letter();
            letter.setPosition(i);
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
