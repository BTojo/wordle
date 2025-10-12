package ru.wordle.infrastructure.presentation;

import ru.wordle.domain.model.Attempt;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.Letter;

import java.util.*;

public class OutputToConsole {

    private final Scanner CONSOLE;
    private static final String HELLO = "The word is hidden...";
    private static final String FALL = "You lose! :( \nThe hidden word was: ";
    private static final String WRONG_WORD = "Invalid characters";
    private static final String IS_NO_WORD = "There is no such word in the dictionary :(";
    private static final String WIN = "YOU WIN!";
    private static final String MESSAGE_ENTER_WORD = "Enter the word: ";
    private static final String THREE_DOTS = "...";

    private static final String NEW_GAME = "\nPlay again? \n(Y/N)";

    private List<String> allAanswers = new ArrayList<>();
    private Set<String> allCharNotPlace = new TreeSet<>();
    private Set<Character> allMissingLetters = new TreeSet<>();

    public OutputToConsole(Scanner console) {
        CONSOLE = console;
    }

    public List<String> getAllAanswers() {
        return allAanswers;
    }

    public Set<String> getAllCharNotPlace() {
        return allCharNotPlace;
    }

    public Set<Character> getAllMissingLetters() {
        return allMissingLetters;
    }


    public String getEnterWord() {
        showMessageEnterWord();
        return CONSOLE.nextLine().trim().toLowerCase();
    }

    public void showIsNoWord() {
        System.out.println(IS_NO_WORD);
    }

    public void showMessageEnterWord() {
        System.out.print(MESSAGE_ENTER_WORD);
    }

    public void showHello() {
        System.out.println(HELLO);
    }

    public void showFall(String randomWord) {
        System.out.println(FALL + randomWord);
    }

    public void showWin() {
        System.out.println(WIN);
    }

    public void showWrongWord() {
        System.out.println(WRONG_WORD);
    }

    public String showGameState(Game game) {
        if (allAanswers.isEmpty()) {
            this.allAanswers = isAnswerInitialized();
        }
        editAnswer();

        for (int i = game.getAttemptsList().size() - 1; i >= 0; i--) {
            Attempt attempt = game.getAttemptsList().get(i);

            for (int j = 0; j < attempt.getLetters().size(); j++) {
                Letter letter = attempt.getLetters().get(j);
                String ch = String.valueOf(letter.getValue());
                Letter.LetterStatus letterStatus = letter.getStatus();

                if (letterStatus == Letter.LetterStatus.IN_PLACE) {
                    allAanswers.set(j, ch);
                }
                if (letterStatus == Letter.LetterStatus.NOT_PLACE) {
                    allCharNotPlace.add(ch);
                    if (game.isAllLetterPresent(String.valueOf(getAllAanswers()), ch)) {
                        allCharNotPlace.remove(ch);
                    }
                }
                if (letterStatus == Letter.LetterStatus.MISSING) {
                    allMissingLetters.add(ch.charAt(0));
                }
            }
        }

        return "Answer:  \"" +
                getAllAanswers().toString() + "\" There are such letters: \"" +
                getAllCharNotPlace().toString() + "\" There are no such letters in the word: \"" +
                getAllMissingLetters().toString() + "\"";
    }

    public void showNewGame() {
        System.out.print(NEW_GAME);
    }

    private List<String> editAnswer() {
        List<String> lastAnswer = getAllAanswers();
        List<String> newAnswer = lastAnswer;

        for (int i = 0; i < lastAnswer.size(); i++) {
            newAnswer.set(i, lastAnswer.get(i).replace(" ", THREE_DOTS));
        }
        return newAnswer;
    }

    private ArrayList isAnswerInitialized() {
        ArrayList<String> answer = new ArrayList<>();
        for (int i = 0; i < Game.NUMBER_OF_LETTERS; i++) {
            answer.add(i, " ");
        }
        return answer;
    }

    public void reload() {
        allAanswers.clear();
        allCharNotPlace.clear();
        allMissingLetters.clear();
    }

}