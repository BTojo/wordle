package logic;

import java.util.ArrayList;
import java.util.List;

public class Game {

    static final int NUMBER_OF_ATTEMPTS = 5;
    public static final int NUMBER_OF_LETTERS = 5;
//    private final Set<String> charNotPlace = new TreeSet<>();
//    private final Set<Character> missingLetters = new TreeSet<>();
//    private final List<String> answer = new ArrayList<>();
//    private final List<Object> usedAttempts = new ArrayList<>();
   private GameStatus gameStatus = GameStatus.PROCESS;
   private final String hiddenWord;

    //    List<Letter> lettersList = new ArrayList<>();

    private final List<Attempt> attemptsList = new ArrayList<>();


    public Game(String randomWord) {
        this.hiddenWord = randomWord;
        System.out.println("**" + hiddenWord);
  //      isAnswerInitialized();
    }

    public List<Attempt> getAttemptsList() {
        return attemptsList;
    }

//    public List<Letter> getLettersList() {
//        return lettersList;
//    }

    public void makeAttempt(String enterWord) {
        Attempt attempt = new Attempt();
        Letter letter = new Letter();
        //       addAttempts(enterWord);

        if (isMatched(enterWord)) {
            setGameStatus(GameStatus.WIN);
        }
        attempt.setLetters(check(enterWord, attempt));
        attemptsList.add(attempt);

        if ((attemptsList.size() + 1) == NUMBER_OF_ATTEMPTS) {
            setGameStatus(GameStatus.LOSE);
        }

    }

    private List<Letter> check(String enterWord, Attempt attempt) {
        List <Letter> letters= new ArrayList<>();
      //  lettersList.clear();

        char charIsRandomWord;
        char charIsEnterWord;

        for (int i = 0; i < hiddenWord.length(); i++) {
            Letter letter = new Letter();
            charIsRandomWord = hiddenWord.charAt(i);
            charIsEnterWord = enterWord.charAt(i);

            if (isCharIsInItsPlace(charIsRandomWord, charIsEnterWord)) {
                letter.setValue(charIsRandomWord);
                letter.setStatus(Letter.LetterStatus.IN_PLACE);


//                if (answer.get(i).isEmpty()) {
//                    answer.set(i, String.valueOf(charIsRandomWord));
//                }
            } else if (isCharBelongsWord(charIsEnterWord)) {
//                charNotPlace.add(String.valueOf(charIsEnterWord));

//                if (charactersInWord(hiddenWord, charIsEnterWord) ==
//                        (countingChar(hiddenWord, charIsEnterWord))) {
//                    charNotPlace.remove(String.valueOf(charIsEnterWord));
//                }
                letter.setValue(charIsEnterWord);
                letter.setStatus(Letter.LetterStatus.NOT_PLACE);


            } else {
           //     missingLetters.add(enterWord.charAt(i));
                letter.setValue(charIsEnterWord);
                letter.setStatus(Letter.LetterStatus.MISSING);
            }
            letters.add(letter);
        }
        System.out.println("letters + " + letters);
        return letters;
    }

    private int charactersInWord(String hiddenWord, char charIsEnterWord) {
        int count = 0;
        for (char c : hiddenWord.toCharArray()) {
            if (c == charIsEnterWord) {
                count++;
            }
        }
        return count;
    }


//    private void isAnswerInitialized() {
//        for (int i = 0; i < Game.NUMBER_OF_LETTERS; i++) {
//            answer.add(i, "");
//        }
//    }

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
        return enterWord.length() == NUMBER_OF_LETTERS;
    }

//    public void addAttempts(String enterWord) {
//        usedAttempts.add(enterWord);
//    }

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

//    public Set<Character> getMissingLetters() {
//        return missingLetters;
//    }

//    public Set<String> getCharNotPlace() {
//        return charNotPlace;
//    }
}