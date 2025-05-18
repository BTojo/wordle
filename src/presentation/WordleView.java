package presentation;

import logic.Attempt;
import logic.Game;
import datastorage.Storage;
import logic.Letter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class WordleView {


    private Storage storage = new Storage();
    private OutputToConsole outputToConsole = new OutputToConsole();
    private Game game;

    private ListStorage listStorage = new ListStorage();

    public void start() {
        String randomWord = storage.getRandomWord();
        game = new Game(randomWord);
        ListStorage listStorage = new ListStorage();

        outputToConsole.showHello();
        while (game.isInProgress()) {
            String enterWord;
            boolean result;

            enterWord = outputToConsole.getEnterWord();
            result = game.validateWord(enterWord);
            if (!result) {
                outputToConsole.showWrongWord();
                continue;
            }

            if (!storage.isExists(enterWord)) {
                outputToConsole.showIsNoWord();
                continue;
            }

            game.makeAttempt(enterWord);
            listStorage.addListStorage(game.getAttemptsList(), game);
            System.out.println(outputToConsole.showGameState(listStorage));
        }

        if (game.isWin()) {
            outputToConsole.showWin();
        } else {
            outputToConsole.showFall(game.getHiddenWord());
        }






//    List<Attempt> lettersList = game.getAttemptsList();
//        for (int i = 0; i<game.getHiddenWord().length();i++){
//        String ch = String.valueOf(lettersList.get(i).getValue());
//        if (lettersList.get(i).getStatus() == Letter.LetterStatus.IN_PLACE) {
//            answer.set(i, ch);
//        } else if ((lettersList.get(i).getStatus() == Letter.LetterStatus.NOT_PLACE)) {
//            charNotPlace.add(ch);
//        } else {
//            missingLetters.add(ch);
//        }
//    }
//
//


    }


    public static class GameState {

        private final Set<String> charNotPlace = new TreeSet<>();
        private final Set<Character> missingLetters = new TreeSet<>();
        private final List<String> answer = new ArrayList<>();
    }
}

