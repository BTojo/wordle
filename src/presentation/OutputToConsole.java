package presentation;

import java.util.*;

public class OutputToConsole {

    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final String HELLO = "The word is hidden...";
    private static final String FALL = "You lose! :( \nThe hidden word was: ";
    private static final String WRONG_WORD = "Invalid characters";
    private static final String IS_NO_WORD = "There is no such word in the dictionary :(";
    private static final String WIN = "YOU WIN!";
    private static final String MESSAGE_ENTER_WORD = "Enter the word: ";
    private static final String THREE_DOTS = "...";

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

    public String showGameState(ListStorage listStorage) {

        return "Answer:  \"" +
                editAnswer(listStorage).toString() + "\" There are such letters: \"" +
                listStorage.getCharNotPlace().toString() + "\" There are no such letters in the word: \"" +
                listStorage.getMissingLetters().toString() + "\"";
    }

    private List<String> editAnswer(ListStorage listStorage) {
        List<String> lastAnswer = listStorage.getAnswer();
        List<String> newAnswer = lastAnswer;

        for (int i = 0; i < lastAnswer.size(); i++) {
            newAnswer.set(i, lastAnswer.get(i).replace(" ", THREE_DOTS));
        }

        return newAnswer;
    }
}

