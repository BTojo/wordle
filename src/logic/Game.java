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
    private final ArrayList<Object> usedAttempts = new ArrayList<>();

    private GameStatus gameStatus = GameStatus.PROCESS;

    private int attemptNumber;
    private final String hiddenWord;

    List<Letter> lettersList = new ArrayList<>();
    private final Attempt attempt = new Attempt();
    private final List<Attempt> attemptsList = new ArrayList<>();


    public Game(String randomWord) {
        this.hiddenWord = randomWord;
 //       System.out.println("**" + randomWord + "**");
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
             if (isCharBelongsWord(charIsEnterWord)) {
                charNotPlace.add(String.valueOf(charIsEnterWord));

                if (charactersInList(answer, charIsEnterWord) ==
                        (countingChar(hiddenWord, charIsEnterWord))) {
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
        System.out.println("lettersList in game: " + lettersList);
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
            attemptsList.add(attempt);
        }
    }

    private int charactersInList (ArrayList<String> answer, char charIsEnterWord) {
        int count = 0;
        for (String c : answer) {
            if (c.equals(String.valueOf(charIsEnterWord))) {
                count++;
            }
        }
        return count;
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

    public int countingChar(String str, char ch) {
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