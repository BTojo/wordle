package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Game {

    static final int NUNBER_OF_ATTEMPTS = 5;
    public static final int NUNBER_OF_LETTERS = 5;
    private final Set<String>  charNotPlace = new TreeSet<>();
    private final Set<Character> missingLetters = new TreeSet<>();
    private final ArrayList<String> answer = new ArrayList<>();
    private ArrayList usedAttempts = new ArrayList<>();

    private GameStatus gameStatus = GameStatus.PROCESS;

    private int attemptNumber;
    private String hiddenWord;

    private Letter letter = new Letter();
    List<Letter> lettersList = new ArrayList<>();
    private Attempt attempt = new Attempt();
    private List<Attempt> attemptsList = new ArrayList<>();




    public Game(String randomWord) {
        this.hiddenWord = randomWord;
        isAnswerInitialized();
    }

    private List<Letter> check (String enterWord) {
        lettersList.clear();


        char charIsRandomWord;
        char charIsEnterWord;

        for (int i = 0; i < hiddenWord.length(); i++) {
            Letter letter = new Letter();
            charIsRandomWord = hiddenWord.charAt(i);
            charIsEnterWord = enterWord.charAt(i);

            if (isCharIsInItsPlace(charIsRandomWord, charIsEnterWord)) {
                letter.setValue(charIsRandomWord);
                letter.setStatus(Letter.LetterStatus.IN_PLACE);

                if (answer.get(i).isEmpty()) {
                    answer.set(i, String.valueOf(charIsRandomWord));
                }
            }

            else if (isCharBelongsWord(charIsEnterWord)) {
                charNotPlace.add(String.valueOf(charIsEnterWord));
                if (countingСhar(String.valueOf(answer), charIsEnterWord) == (countingСhar(String.valueOf(hiddenWord), charIsEnterWord))) {
                    charNotPlace.remove(String.valueOf(charIsEnterWord));
                }
                letter.setValue(charIsEnterWord);
                letter.setStatus(Letter.LetterStatus.NOT_PLACE);
            } else {
                missingLetters.add(enterWord.charAt(i));
                letter.setValue(charIsEnterWord);
                letter.setStatus(Letter.LetterStatus.MISSING);
            }
            lettersList.add(letter);
        }
        System.out.println("69 lettersList in game: " + lettersList);
        return lettersList;
    }

    public void makeAttempt(String enterWord) {
        addAttempts(enterWord);


        if (getNunberOfAttempts() == NUNBER_OF_ATTEMPTS) {
            setGameStatus(GameStatus.LOSE);
        }

        if (isMatched(enterWord)) {
            setGameStatus(GameStatus.WIN);
        } else {
            attempt.setLetters(check(enterWord));
            //     Attempt attempt = new Attempt();
            attemptsList.add(attempt);
  //          attempt.setLetters(lettersList);

            System.out.println("!!! -->");
            System.out.println(attemptsList.toString());
//            for (Attempt a : attempt) {
//                System.out.println(a);
//            }

            System.out.println("<--!!!");
        }
    }

    private void isAnswerInitialized() {
        for (int i = 0; i < Game.NUNBER_OF_LETTERS; i++) {
            answer.add(i, "");
        }
    }

    public boolean isInProgress() {
        return gameStatus == GameStatus.PROCESS;
    }

    public boolean validateWord(String enterWord) {
        return (isNumberOfCharacters(enterWord) && isEnglishAlphabetOnly(enterWord));
    }

    public boolean isEnglishAlphabetOnly(String enterWord) {
        return enterWord.trim().matches("[A-Za-z]+");
    }

    public boolean isNumberOfCharacters(String enterWord) {
        return enterWord.length() == NUNBER_OF_LETTERS;
    }

    public void addAttempts(String enterWord) {
        usedAttempts.add(enterWord);
        attemptNumber++;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public boolean isWin() {
        return (getGameStatus() == GameStatus.WIN);
    }

    public String getHiddenWord() {
        return hiddenWord;
    }

    public boolean isMatched(String enterWord) {
        return getHiddenWord().equals(enterWord);
    }

    public boolean isCharIsInItsPlace(char randomWord, char enterWord) {
        return randomWord == enterWord;
    }

    public boolean isCharBelongsWord(char ch) {
        return (getHiddenWord().contains(String.valueOf(ch)));
    }

    public int countingСhar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    public int getNunberOfAttempts() {
        return attemptNumber;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public Set<Character> getMissingLetters() {
        return missingLetters;
    }

    public Set<String> getCharNotPlace() {
        return charNotPlace;
    }


}