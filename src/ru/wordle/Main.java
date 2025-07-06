package ru.wordle;

import ru.wordle.datastorage.StorageException;
import ru.wordle.presentation.WordleView;

public class Main {

    public static void main(String[] args) throws StorageException {
        WordleView wordleView = new WordleView();
        wordleView.start();

    }
}