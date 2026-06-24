package ru.wordle.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class Game {

    static final int NUMBER_OF_ATTEMPTS = 6;
    public static final int NUMBER_OF_LETTERS = 5;

    private String gameId;
    private String ownerId;
    private OffsetDateTime createdAt;
    private GameStatus gameStatus = GameStatus.IDLE;
    private final String secretWord;
    private final List<Attempt> attemptsList = new ArrayList<>();

    public Game(String secretWord) {
        this.secretWord = secretWord;
        this.createdAt = OffsetDateTime.now();
        this.gameStatus = GameStatus.IDLE;
    }

    public MakeAttemptResult makeAttempt(String enterWord) {
        if (!isInProgress()) {
            return MakeAttemptResult.failure(MakeAttemptError.GAME_FINISHED);
        }

        if (attemptsList.size() >= NUMBER_OF_ATTEMPTS) {
            return MakeAttemptResult.failure(MakeAttemptError.NO_ATTEMPTS_LEFT);
        }

        Attempt attempt = new Attempt();
        attempt.setGameId(this.gameId);
        attempt.setAttemptNumber(attemptsList.size() + 1);
        attempt.setLetters(check(enterWord));

        attemptsList.add(attempt);

        if (isMatched(enterWord)) {
            setGameStatus(GameStatus.GAME_WIN);
        } else if (attemptsList.size() == NUMBER_OF_ATTEMPTS) {
            setGameStatus(GameStatus.GAME_LOSING);
        }

        return MakeAttemptResult.success(attempt);
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
                letter.setStatus(LetterStatus.RIGHT_POSITION);
            } else if (isCharBelongsWord(charIsEnterWord)) {
                letter.setValue(charIsEnterWord);
                letter.setStatus(LetterStatus.WRONG_POSITION);
            } else {
                letter.setValue(charIsEnterWord);
                letter.setStatus(LetterStatus.NOT_PRESENT);
            }
            letters.add(letter);
        }
        return letters;
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.IDLE;
    }

    public boolean isWin() {
        return gameStatus == GameStatus.GAME_WIN;
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