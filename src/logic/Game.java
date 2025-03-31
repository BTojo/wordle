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
    private Storage storage = new Storage();
    private String threeDots = "...";
    private ArrayList<String> answerEmpty = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));
    private static OutputToConsole outputToConsole = new OutputToConsole();
    private GameStatus gameStatus = GameStatus.PROCESS;
    private String hiddenWord;

    String enterWord;

    private String getReturnString() {
        return "Answer:  \"" +
                answerEmpty.toString() + "\" There are such letters: \"" +
                charNotPlace + "\" There are no such letters in the word: \"" +
                missingLetters + "\"";
    }

    public void start() {
        hiddenWord = storage.getRandomWord();

        outputToConsole.showHello();
        for (int i = 0; i <= NUNBER_OF_ATTEMPTS; i++) {
            if (i == NUNBER_OF_ATTEMPTS) {
                gameStatus = GameStatus.LOSE;
                break;
            }
            enterWord();

            if (isMatched(enterWord)) {
                gameStatus = GameStatus.WIN;
                break;
            }
            check(getHiddenWord(), enterWord);
        }
        if (gameStatus == GameStatus.WIN) {
            outputToConsole.showWin();
        } else {
            outputToConsole.showFall(getHiddenWord());
        }
    }

    public boolean isMatched(String enterWord) {
        return getHiddenWord().equals(enterWord);
    }

    public String enterWord() {
        while (true) {
            outputToConsole.showMessageEnterWord();
            enterWord = outputToConsole.getEnterWord();

            if (storage.isExists(enterWord)) {
                return enterWord;
            } else {
                outputToConsole.showIsNoWord();
            }
        }
    }

    public boolean isCharIsInItsPlace(char randomWord, char enterWord) {
        return randomWord == enterWord;
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

    public String getThreeDots() {
        return threeDots;
    }

    public String getHiddenWord() {
        return hiddenWord;
    }
}