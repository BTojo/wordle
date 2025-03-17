package presentation;
import java.util.*;

public class OutputToConsole {
    //   static String randomWord;
    private static String enterWord;
    private static String threeDots = "...";
    static List answer = new ArrayList<>(List.of(threeDots, threeDots, threeDots, threeDots, threeDots));

    static Scanner console = new Scanner(System.in);

    private static final String hello = "The word is hidden...";
    private static final String fall = "You lose! :( \nThe hidden word was: ";

    private static final String isNoWord = "There is no such word in the dictionary :(";

    private static final String win = "YOU WIN!";


    private static final String messageEnterWord = "Enter the word: ";


    public void setEnterWord() {
        enterWord = console.nextLine().trim().toLowerCase();
    }

    public String getEnterWord() {
        return enterWord;
    }

    public static void getIsNoWord() {
        System.out.println(isNoWord);
    }

    public static void getMessageEnterWord() {
        System.out.print(messageEnterWord);
    }

    public static void getHello() {
        System.out.println(hello);
    }

    public static void getFall(String randomWord) {
        System.out.println(fall + randomWord);
    }

    public static void getWin() {
        System.out.println(win);
    }

    public List getAnswer() {
        return answer;
    }
}