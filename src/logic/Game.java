package logic;

import datastorage.Storage;
import presentation.OutputToConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Game {

    private Set<String> charNotPlace = new TreeSet<>();
    private Set<Character> missingLetters = new TreeSet<>();

    static final int NUNBER_OF_ATTEMPTS = 5;

    static final int NUNBER_OF_LETTERS = 5;
    private Storage storage = new Storage();
    private String threeDots = "...";
    private ArrayList<String> answerEmpty = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));
    private static OutputToConsole outputToConsole = new OutputToConsole();

    private int attemptNumber;

    private GameStatus gameStatus = GameStatus.PROCESS;

    private String hiddenWord;
    private int attempt;

    private ArrayList usedAttempts = new ArrayList<>();

    public Game(String randomWord) {
        this.hiddenWord = randomWord;
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
        return enterWord.length() == NUNBER_OF_LETTERS;
    }

    public void addAttempts(String enterWord) {
        usedAttempts.add(enterWord);
    }

    public int addAttempt() {
        return ++attemptNumber;
    }

    public void makeAttempt(String enterWord) {
        addAttempt();
        addAttempts(enterWord);
        if (getNunberOfAttempts() == NUNBER_OF_ATTEMPTS ) {
            setGameStatus(GameStatus.LOSE);
        }
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean isWin(){
        return (getGameStatus() == GameStatus.WIN);
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public boolean isMatched(String enterWord) {
        return getHiddenWord().equals(enterWord);
    }

    public void check(String randomWord, String enterWord) {
        char r;
        char ch;

        for (int i = 0; i < randomWord.length(); i++) {
            r = randomWord.charAt(i);
            ch = enterWord.charAt(i);

            if (isCharIsInItsPlace(r, ch) && (getThreeDots().equals(answerEmpty.get(i)))) {
                answerEmpty.set(i, String.valueOf(r));
            }

            if (isCharBelongsWord(ch)) {
                charNotPlace.add(String.valueOf(ch));
                if (countingСhar(String.valueOf(answerEmpty), ch) == (countingСhar(String.valueOf(randomWord), ch))) {
                    charNotPlace.remove(String.valueOf(ch));
                }
            } else {
                missingLetters.add(enterWord.charAt(i));
            }
        }
        System.out.println(getReturnString());
    }

    public boolean isCharIsInItsPlace(char randomWord, char enterWord) {
        return randomWord == enterWord;
    }

    public String getThreeDots() {
        return threeDots;
    }

    public boolean isCharBelongsWord(char ch) {
        return (getHiddenWord().contains(String.valueOf(ch)));
    }

    public int countingСhar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    private String getReturnString() {          //перенести в консоль
        return "Answer:  \"" +
                answerEmpty.toString() + "\" There are such letters: \"" +
                charNotPlace + "\" There are no such letters in the word: \"" +
                missingLetters + "\"";
    }

    public int getNunberOfAttempts() {
        return attemptNumber;
    }

}