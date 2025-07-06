package ru.wordle.presentation;

import ru.wordle.datastorage.StorageException;
import ru.wordle.logic.Game;

import java.util.Scanner;

public class WordleView {
    private Game game;

    private Storage storage = new Storage();
    private OutputToConsole outputToConsole = new OutputToConsole();
    public void setGame(Game game) {
        this.game = game;
    }
    public WordleView() throws StorageException {
    }

    public void start() {
        String randomWord = storage.getRandomWord();
        game = new Game(randomWord);
        ListStorage listStorage;

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
            listStorage = new ListStorage(game);
            System.out.println(outputToConsole.showGameState(listStorage));
        }

        if (game.isWin()) {
            outputToConsole.showWin();
        } else {
            outputToConsole.showFall(game.getHiddenWord());
        }

        outputToConsole.showNewGame();
        newGame();

    }

    private void newGame () {
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("Y")) {
            System.out.println();
            start();
        } else {
            System.out.println("Game over");
        }


    }
}