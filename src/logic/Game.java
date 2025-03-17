package logic;

import datastorage.Storage;
import presentation.OutputToConsole;

import java.util.*;

public class Game {

    static Set<String> charNotPlace = new TreeSet<>();
    static Random randomizer = new Random();
    static Set<Character> missingLetters = new TreeSet<>();

    static final int numberOfAttempts = 5;
    private static Storage storage = new Storage();
    static ArrayList<String> answer = storage.getAnswer();


    private static OutputToConsole outputToConsole = new OutputToConsole();

    private static String getReturnString() {
        return "Answer:  \"" +
                answer.toString() + "\" There are such letters: \"" +
                charNotPlace + "\" There are no such letters in the word: \"" +
                missingLetters + "\"";
    }

    public static void start() {

        outputToConsole.getHello();

        for (int i = 0; i <= numberOfAttempts; i++) {
            if (i == numberOfAttempts) {
                outputToConsole.getFall(storage.getRandomWord());
                break;
            }
            enterWord();

            if (matched(storage.getRandomWord(), outputToConsole.getEnterWord())) {
                outputToConsole.getWin();
                break;
            }
            check(storage.getRandomWord(), outputToConsole.getEnterWord());
        }
    }

    public static boolean matched(String randomWord, String enterWord) {
        boolean b = randomWord.equals(enterWord);
        return b;
    }

    public static String enterWord() {
        while (true) {
            outputToConsole.getMessageEnterWord();
            outputToConsole.setEnterWord();

            if (storage.words.contains(outputToConsole.getEnterWord())) {
                break;
            } else {
                outputToConsole.getIsNoWord();
            }
        }
        return outputToConsole.getEnterWord();
    }

    public static boolean charIsInItsPlace(char randomWord, char enterWord) {
        if (randomWord == enterWord) {
            return true;
        }
        return false;
    }

    public static boolean charBelongsWord(String word, char ch) {
        if (word.contains(String.valueOf(ch))) {
            return true;
        }
        return false;
    }

    public static int countingСhar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    public static void check(String randomWord, String enterWord) {
        char r;
        char e;

        for (int i = 0; i < randomWord.length(); i++) {
            r = randomWord.charAt(i);
            e = enterWord.charAt(i);

            if (charIsInItsPlace(r, e) && (storage.getThreeDots().equals(answer.get(i)))) {
                answer.set(i, String.valueOf(r));
            }

            if (charBelongsWord(randomWord, e)) {
                charNotPlace.add(String.valueOf(e));
                if (countingСhar(String.valueOf(answer), e) == (countingСhar(String.valueOf(randomWord), e))) {
                    charNotPlace.remove(String.valueOf(e));
                }
            } else {
                missingLetters.add(enterWord.charAt(i));
            }
        }
        System.out.println(getReturnString());
    }
}