package presentation;

import logic.Game;
import logic.GameStatus;
import datastorage.Storage;

public class WordleView {

    private Storage storage = new Storage();
    private OutputToConsole outputToConsole = new OutputToConsole();
    private Game game;
    private String enterWord;
    private boolean result;

    public void start() {
        String randomWord = storage.getRandomWord();
        game = new Game(randomWord);

        outputToConsole.showHello();
        while (game.isInProgress()) {
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

            if (game.getGameStatus() != GameStatus.PROCESS) {
                break;
            }

            if (game.isMatched(enterWord)) {
                game.setGameStatus(GameStatus.WIN);
                break;
            }

            game.check(game.getHiddenWord(), enterWord);
            System.out.println(outputToConsole.getReturnString(game));
        }

        if (game.isWin()) {
            outputToConsole.showWin();
        } else {
            outputToConsole.showFall(game.getHiddenWord());
        }
    }
}