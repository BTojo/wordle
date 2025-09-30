package ru.wordle.datastorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.wordle.datastorage.StorageException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Storage {
    static Random randomizer = new Random();
    public List<String> words = new ArrayList<>();

    public Storage() throws StorageException {
        loadWorlds();
    }

    private void loadWorlds() throws StorageException {

        try {
            // Загружаем файл из classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("wordle.txt");
            if (inputStream == null) {
                throw new StorageException("The file wordle.txt not found.");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    words.add(line.trim());
                }
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