package presentation;

import logic.Game;
import logic.Letter;

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

//    public String getReturnString(Game game) {
//        return "Answer:  \"" +
//                setAnswer(game).toString() + "\" There are such letters: \"" +
//                game.getCharNotPlace() + "\" There are no such letters in the word: \"" +
//                game.getMissingLetters() + "\"";
//    }


    public String showGameState(ListStorage listStorage) {
     //   public String getReturnString(Game game) {

            return "Answer:  \"" +
                    listStorage.getAnswer().toString() + "\" There are such letters: \"" +
                    listStorage.getCharNotPlace().toString() + "\" There are no such letters in the word: \"" +
                    listStorage.getMissingLetters().toString() + "\"";
        }



//    public ArrayList<String> setAnswer (Game game) {
//        ArrayList <String> answer = isAnswerInitialized();
//        return answer;
//         }

    }  //

//        List<Letter> lettersList = game.getLettersList();
//        for (int i = 0; i < game.getHiddenWord().length(); i++) {
//            String ch = String.valueOf(lettersList.get(i).getValue());
//            if (lettersList.get(i).getStatus() == Letter.LetterStatus.IN_PLACE) {
//                answer.set(i, ch);
////            } else if ((lettersList.get(i).getStatus() == Letter.LetterStatus.NOT_PLACE)) {
////                charNotPlace.add(ch);
////            } else {
////                missingLetters.add(ch);
//            }
//        }
//
//        return answer;
//    }
