package ru.wordle.infrastructure.presentation;

import ru.wordle.infrastructure.datastorage.StorageException;
import ru.wordle.domain.model.Game;
import ru.wordle.domain.model.GameFactory;
import ru.wordle.infrastructure.datastorage.Storage;

import java.util.Scanner;

public class WordleView {
    private Game game;
    private GameFactory gameFactory;

    private final Storage storage;
    private OutputToConsole outputToConsole;
    private final Scanner scanner;

    public void setGame(Game game) {
        this.game = game;
    }

    public WordleView(Scanner scanner, OutputToConsole outputToConsole, GameFactory gameFactory, Storage storage, Game game) throws StorageException {
        this.scanner = scanner;
        this.outputToConsole = outputToConsole;
        this.gameFactory = gameFactory;
        this.storage = storage;
        this.game = game;


    }

    public void start() {
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
            System.out.println(outputToConsole.showGameState(game));
        }

        if (game.isWin()) {
            outputToConsole.showWin();
        } else {
            outputToConsole.showFall(game.getHiddenWord());
        }

        outputToConsole.showNewGame();
        newGame();

    }

    private void newGame() {
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("Y")) {
            System.out.println();
            game = gameFactory.create();
            outputToConsole.reload();
            start();

        } else {
            System.out.println("Game over");
        }
    }
}