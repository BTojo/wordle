package presentation;

import logic.Game;
import java.util.ArrayList;
import java.util.Scanner;

public class OutputToConsole {

    private static final Scanner CONSOLE = new Scanner(System.in);
    private static final String HELLO = "The word is hidden...";
    private static final String FALL = "You lose! :( \nThe hidden word was: ";
    private static final String WRONG_WORD = "Invalid characters";
    private static final String IS_NO_WORD = "There is no such word in the dictionary :(";
    private static final String WIN = "YOU WIN!";
    private static final String MESSAGE_ENTER_WORD = "Enter the word: ";
    private static final String THREE_DOTS = "...";

    private ArrayList isAnswerInitialized() {
        ArrayList<String> answer =new ArrayList<>();
        for (int i = 0; i < Game.NUNBER_OF_LETTERS; i++) {
            answer.add(i, THREE_DOTS);
        }
        return answer;
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

    public String getReturnString(Game game) {          //перенести в консоль
        return "Answer:  \"" +
                setAnswer(game).toString() + "\" There are such letters: \"" +
                game.getCharNotPlace() + "\" There are no such letters in the word: \"" +
                game.getMissingLetters() + "\"";
    }
    
    public ArrayList setAnswer (Game game){
        ArrayList<String> answer = new ArrayList<>();
        if (answer.isEmpty()) {
            answer = isAnswerInitialized();
        }
        for (int i = 0; i < Game.NUNBER_OF_LETTERS; i++) {
            if (game.getAnswer().get(i).isEmpty()) {
                answer.set(i, THREE_DOTS);
            } else {
                answer.set(i, game.getAnswer().get(i));
            }
        }
        return answer;
    }
}