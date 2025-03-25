package logic;
import datastorage.Storage;
import presentation.OutputToConsole;
import java.util.*;

public class Game {

    Set<String> charNotPlace = new TreeSet<>();
    Set<Character> missingLetters = new TreeSet<>();
    static final int NUNBER_OF_ATTEMPTS = 5;
    private Storage storage = new Storage();
    private String threeDots = "...";
    private ArrayList<String> answerEmpty = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));
    private static OutputToConsole outputToConsole = new OutputToConsole();
    GameStatus gameStatus = GameStatus.PROCESS;

    String enterWord;

    private String getReturnString() {
        return "Answer:  \"" +
                answerEmpty.toString() + "\" There are such letters: \"" +
                charNotPlace + "\" There are no such letters in the word: \"" +
                missingLetters + "\"";
    }

    public void start() {

        outputToConsole.showHello();

        for (int i = 0; i <= NUNBER_OF_ATTEMPTS; i++) {
            if (i == NUNBER_OF_ATTEMPTS) {
                gameStatus = GameStatus.LOSE;
                break;
            }
            enterWord();

            if (isMatched(storage.getHiddenWord(), enterWord)) {
                gameStatus = GameStatus.WIN;
                break;
            }
            check(storage.getHiddenWord(), enterWord);
        }
        if (gameStatus == GameStatus.WIN) {
            outputToConsole.showWin();
        } else {
            outputToConsole.showFall(storage.getHiddenWord());
        }
    }

    public boolean isMatched(String randomWord, String enterWord) {
        boolean b = randomWord.equals(enterWord);
        return b;
    }

    public String enterWord() {
        while (true) {
            outputToConsole.showMessageEnterWord();
            enterWord = outputToConsole.getEnterWord();

            if (storage.isExists(enterWord)) {
                break;
            } else {
                outputToConsole.showIsNoWord();
            }
        }
        return enterWord;
    }

    public boolean isCharIsInItsPlace(char randomWord, char enterWord) {
        if (randomWord == enterWord) {
            return true;
        }
        return false;
    }

    public boolean isCharBelongsWord(String word, char ch) {
        return (word.contains(String.valueOf(ch)));
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
        char e;

        for (int i = 0; i < randomWord.length(); i++) {
            r = randomWord.charAt(i);
            e = enterWord.charAt(i);

            if (isCharIsInItsPlace(r, e) && (getThreeDots().equals(answerEmpty.get(i)))) {
                answerEmpty.set(i, String.valueOf(r));
            }

            if (isCharBelongsWord(randomWord, e)) {
                charNotPlace.add(String.valueOf(e));
                if (countingСhar(String.valueOf(answerEmpty), e) == (countingСhar(String.valueOf(randomWord), e))) {
                    charNotPlace.remove(String.valueOf(e));
                }
            } else {
                missingLetters.add(enterWord.charAt(i));
            }
        }
        System.out.println(getReturnString());
    }

    public ArrayList<String> getAnswerEmpty() {
        return answerEmpty;
    }

    public String getThreeDots() {
        return threeDots;
    }
}