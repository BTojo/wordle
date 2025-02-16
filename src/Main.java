import java.util.*;
import java.io.*;


public class Main {
    static String randomWord;
    static String enterWord;
    static String threeDots = "...";
    static List answer = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));
    static List<String> words = new ArrayList<>();
    static Set<String> charNotPlace = new TreeSet<>();
    static Random randomizer = new Random();
    static Set<Character> missingLetters = new TreeSet<>();
    static Scanner console = new Scanner(System.in);


    public static void main(String[] args) throws IOException {
        getWorlds();
        getRandomWord();
        System.out.println("The word is hidden...");

        for (int i = 0; i < 6; i++) {
            if (i == 5) {
                fall();
                break;
            }
            getEnterWord();

            if (matched(randomWord, enterWord)) {
                win();
                break;
            }
            check(randomWord, enterWord);
        }
    }


    public static String getRandomWord() {
        randomWord = words.get(randomizer.nextInt(words.size()));
//      System.out.println(randomWord);
        return randomWord;
    }

    public static boolean charIsInItsPlace(char randomWord, char enterWord) {
        if (randomWord == enterWord) {
            return true;
        }
        return false;
    }

    public static boolean charBelongsWord(String word, char ch) {
        if (word.contains(String.valueOf(ch))) {
            return true;
        }
        return false;
    }

    public static boolean matched(String randomWord, String enterWord) {
        boolean b = randomWord.equals(enterWord);
        return b;
    }

    public static void win() {
        System.out.println("\uD83D\uDE00YOU WIN!\uD83D\uDE00");
    }

    public static void fall() {
        System.out.println("You lose! :( \nThe hidden word was: " + randomWord);
    }

    public static void returnString() {
        System.out.println("Answer:  \""  +
                answer.toString() + "\" There are such letters: \"" +
                charNotPlace + "\" There are no such letters in the word: \"" +
                missingLetters + "\"");
    }

    public static String getEnterWord() {
        while (true) {
            System.out.print("Enter the word: ");

            enterWord = console.nextLine().trim().toLowerCase();

            if (words.contains(enterWord)) {
                break;
            } else {
                System.out.println("There is no such word in the dictionary :(");
            }
        }
        return enterWord;
    }

    public static int countingСhar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    public static void check(String randomWord, String enterWord) {
        char r;
        char e;

        for (int i = 0; i < randomWord.length(); i++) {
            r = randomWord.charAt(i);
            e = enterWord.charAt(i);

            if (charIsInItsPlace(r, e) && (threeDots.equals(answer.get(i)))) {
                answer.set(i, r);
            }

            if (charBelongsWord(randomWord, e)) {
                charNotPlace.add(String.valueOf(e));
                if (countingСhar(String.valueOf(answer), e) == (countingСhar(String.valueOf(randomWord), e))) {
                    charNotPlace.remove(String.valueOf(e));
                }
            }

            else {
                missingLetters.add(enterWord.charAt(i));
            }
        }
        returnString();
    }

    public static List<String> getWorlds() {

        try (BufferedReader br = new BufferedReader(new FileReader("wordle.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                words.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading from a file" + e.getMessage());
            System.out.println("The word library is not loaded. \n" +
                    " The program is stopped.");
            System.exit(0);
        }
        return words;
    }
}




