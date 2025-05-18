package logic;

import java.util.ArrayList;
import java.util.List;

public class Game {

    static final int NUMBER_OF_ATTEMPTS = 5;
    public static final int NUMBER_OF_LETTERS = 5;
    private GameState gameState = GameState.PROCESS;
    private final String hiddenWord;
    private final List<Attempt> attemptsList = new ArrayList<>();

    public Game(String randomWord) {
        this.hiddenWord = randomWord;
    }

    public List<Attempt> getAttemptsList() {
        return attemptsList;
    }

    public void makeAttempt(String enterWord) {
        Attempt attempt = new Attempt();

        if (isMatched(enterWord)) {
            setGameStatus(gameState.WIN);
        }
        attempt.setLetters(check(enterWord));
        attemptsList.add(attempt);

        if (attemptsList.size() == NUMBER_OF_ATTEMPTS) {
            setGameStatus(gameState.LOSE);
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
        return gameState == GameState.PROCESS;
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

    public GameState getGameStatus() {
        return gameState;
    }

    public void setGameStatus(GameState gameStatus) {
        this.gameState = gameStatus;
    }

    public boolean isWin() {
        return (getGameStatus() == GameState.WIN);
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
}