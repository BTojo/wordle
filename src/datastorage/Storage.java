package datastorage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Storage {
    static String randomWord;
    private static String threeDots = "...";
    private static ArrayList<String> answer = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));


    static Set<String> charNotPlace = new TreeSet<>();
    static Random randomizer = new Random();
    static Set<Character> missingLetters = new TreeSet<>();
    static Scanner console = new Scanner(System.in);

    public static List<String> words = new ArrayList<>();


    public Storage()  {
        loadWorlds();
        setRandomWord();
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
    private String setRandomWord() {

        randomWord = words.get(randomizer.nextInt(words.size()));
        return randomWord;
    }

    public String getRandomWord() {
        return randomWord;
    }

    public ArrayList<String> getAnswer(){
        return answer;
    }

    public String getThreeDots(){
        return threeDots;
    }
}
