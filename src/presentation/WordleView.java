package presentation;

import logic.Game;
import datastorage.Storage;

public class WordleView {

    private Storage storage = new Storage();
    private OutputToConsole outputToConsole = new OutputToConsole();
    private Game game;

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
    }
}