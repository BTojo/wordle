package ru.wordle.logic;

import java.util.ArrayList;
import java.util.List;

public class Game {

    static final int NUMBER_OF_ATTEMPTS = 5;
    public static final int NUMBER_OF_LETTERS = 5;
    private GameStatus gameStatus = GameStatus.PROCESS;
    private final String hiddenWord;
    private final List<Attempt> attemptsList = new ArrayList<>();


    public Game(String randomWord) {
        this.hiddenWord = randomWord;
        System.out.println(hiddenWord);   ///
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
            setGameStatus(gameStatus.WIN);
        }
        attempt.setLetters(check(enterWord));
        attemptsList.add(attempt);

        if (attemptsList.size() == NUMBER_OF_ATTEMPTS) {
            setGameStatus(gameStatus.LOSE);
        }
    }

    private List<Letter> check(String enterWord) {
        List<Letter> letters = new ArrayList<>();

        char charIsRandomWord;
        char charIsEnterWord;

        for (int i = 0; i < hiddenWord.length(); i++) {
            Letter letter = new Letter();
            charIsRandomWord = hiddenWord.charAt(i);
            charIsEnterWord = enterWord.charAt(i);

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

    public boolean validateWord(String enterWord) {
        return (isNumberOfCharacters(enterWord) && isEnglishAlphabetOnly(enterWord));
    }

    public boolean isEnglishAlphabetOnly(String enterWord) {
        return enterWord.trim().matches("[A-Za-z]+");
    }

    public boolean isNumberOfCharacters(String enterWord) {
        return enterWord.length() == NUMBER_OF_LETTERS;
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
        return (getHiddenWord().contains(String.valueOf(ch)));
    }

    public int countingChar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    public boolean isAllLetterPresent (String answer, String ch) {
        if (countingChar(getHiddenWord(), ch.charAt(0)) == countingChar(answer, ch.charAt(0))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameStatus=" + gameStatus +
                ", hiddenWord='" + hiddenWord + '\'' +
                ", attemptsList=" + attemptsList +
                '}';
    }
}