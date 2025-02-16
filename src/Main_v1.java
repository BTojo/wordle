import java.util.*;
import java.io.*;


public class Main_v1 {
    static String randomWord;
    static String enterWord;
    static String threeDots = "...";
    static List answer = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));
    static List<String> words = new ArrayList<>();
    static Set<String> charNotPlace = new TreeSet<>();
    static Random randomizer = new Random();
    static Set<Character> missingLetters = new TreeSet<>();


    public static void main(String[] args) throws IOException {
        getWorlds();
        getRandomWord();
        System.out.println("Слово загадано...");

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
        System.out.println(randomWord);
        return randomWord;
    }

    public static boolean charIsInItsPlace(char randomWord, char enterWord) {
        if (randomWord == enterWord) {
            //       System.out.println("true - символ на своем месте");  //
            return true;
        }
        //    System.out.println("false - символ НЕ на своем месте");   //
        return false;
    }


    public static boolean charBelongsWord(String word, char ch) {
        if (word.contains(String.valueOf(ch))) {
            //   System.out.println("символ имеется в слове!");
            return true;
        }
        //   System.out.println("такого символа нет в слове!");
        return false;
    }

    public static boolean matched(String randomWord, String enterWord) {
        boolean b = randomWord.equals(enterWord);
        return b;
    }

    public static void win() {
        System.out.println("ТЫ ВЫЙГРАЛ!");
    }

    public static void fall() {
        System.out.println("Ты проиграл!:( \nЗагаданное слово было: " + randomWord);
    }

    public static void returnString() {
        System.out.println("Answer: " + '\u0022' +
                answer.toString() + '\u0022' + " There are such letters: " +
                charNotPlace + '\u0022' + " There are no such letters in the word: " +
                missingLetters + '\u0022');
    }


    public static String getEnterWord() {
        while (true) {
            System.out.print("Enter the word: ");
            Scanner console = new Scanner(System.in);
            enterWord = console.nextLine().trim().toLowerCase();

            if (words.contains(enterWord)) {
                //  System.out.println("Есть такое слово: " + enterWord);
                break;
            } else {
                System.out.println("Такого слова нет :(");
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
            System.out.println("Ошибка чтения из файла" + e.getMessage());
            System.out.println("Библиотека слов не загружена. \n Программа остановленна.");
            System.exit(0);
        }
        return words;
    }
}




