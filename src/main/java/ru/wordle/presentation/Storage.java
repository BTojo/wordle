package ru.wordle.presentation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.wordle.datastorage.StorageException;


public class Storage {
    static Random randomizer = new Random();
    public List<String> words = new ArrayList<>();

    public Storage() throws StorageException {
        loadWorlds();
    }

    private void loadWorlds() throws StorageException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("wordle.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            throw new StorageException("Error reading from a file" + e.getMessage() + ". The word library is not loaded. \n \" The program is stopped.\"", e);
        }
    }

    public String getRandomWord() {
        return words.get(randomizer.nextInt(words.size()));
    }

    public boolean isExists(String str) {
        return words.contains(str);
    }
}