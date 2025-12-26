package ru.wordle.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Game {

    static final int NUMBER_OF_ATTEMPTS = 5;
    public static final int NUMBER_OF_LETTERS = 5;

    private String gameId;
    private LocalDateTime createdAt;
    private GameStatus gameStatus = GameStatus.PROCESS;
    private final String hiddenWord;
    private final List<Attempt> attemptsList = new ArrayList<>();

    public Game(String randomWord) {
        this.hiddenWord = randomWord;
        this.createdAt = LocalDateTime.now();
        // System.out.println(hiddenWord);
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSecretWord() {
        return hiddenWord;
    }

    public GameStatus getStatus() {
        return gameStatus;
    }

    public void setStatus(GameStatus status) {
        this.gameStatus = status;
    }

    public void setGameStatusProcess (){
        gameStatus = GameStatus.PROCESS;
    }

    public List<Attempt> getAttemptsList() {
        return attemptsList;
    }

    public void makeAttempt(String enterWord) {
        Attempt attempt = new Attempt();

        if (isMatched(enterWord)) {
            setGameStatus(GameStatus.WIN);
        }
        attempt.setLetters(check(enterWord));
        attemptsList.add(attempt);

        if (attemptsList.size() == NUMBER_OF_ATTEMPTS && gameStatus != GameStatus.WIN) {
            setGameStatus(GameStatus.LOSE);
        }
    }

    private List<Letter> check(String enterWord) {
        List<Letter> letters = new ArrayList<>();
        for (int i = 0; i < hiddenWord.length(); i++) {
            Letter letter = new Letter();
            char charIsRandomWord = hiddenWord.charAt(i);
            char charIsEnterWord = enterWord.charAt(i);

            if (isCharIsInItsPlace(charIsRandomWord, charIsEnterWord)) {
                letter.setValue(charIsRandomWord);
                letter.setStatus(Letter.LetterStatus.IN_PLACE);
            } else if (isCharBelongsWord(charIsEnterWord)) {
                letter.setValue(charIsEnterWord);
                letter.setStatus(Letter.LetterStatus.NOT_PLACE);
            } else {
                letter.setValue(charIsEnterWord);
                letter.setStatus(Letter.LetterStatus.MISSING);
            }
            letters.add(letter);
        }
        return letters;
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.PROCESS;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean isWin() {
        return (getGameStatus() == GameStatus.WIN);
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public boolean isMatched(String enterWord) {
        return getHiddenWord().equals(enterWord);
    }
    public boolean isCharIsInItsPlace(char randomWord, char enterWord) {
        return randomWord == enterWord;
    }

    public boolean isCharBelongsWord(char ch) {
        return getHiddenWord().contains(String.valueOf(ch));
    }
    @Override
    public String toString() {
        return "Game{" +
                "id='" + gameId + '\'' +
                ", status=" + gameStatus +
                ", hiddenWord='" + hiddenWord + '\'' +
                ", attempts=" + attemptsList.size() +
                '}';
    }
}
