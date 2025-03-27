package datastorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Storage {
    private String randomWord;
    static Random randomizer = new Random();
    public List<String> words = new ArrayList<>();

    public Storage()  {
        loadWorlds();
        getRandomWord();
    }

    private void loadWorlds()  {

        try (BufferedReader br = new BufferedReader(new FileReader("wordle.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from a file" + e.getMessage());
            System.out.println("The word library is not loaded. \n" +
                    " The program is stopped.");
            System.exit(0);
        }
    }
    public String getRandomWord() {
        randomWord = words.get(randomizer.nextInt(words.size()));
        return randomWord;
    }


    public boolean isExists (String str) {
        return words.contains(str);
    }
}