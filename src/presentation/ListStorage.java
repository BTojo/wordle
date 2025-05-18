package presentation;

import logic.Attempt;
import logic.Game;
import logic.Letter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ListStorage {

    private List<String> answer = new ArrayList<>();
    private Set<String> charNotPlace = new TreeSet<>();
    private Set<Character> missingLetters = new TreeSet<>();

    public Set<String> getCharNotPlace() {
        return charNotPlace;
    }

    public Set<Character> getMissingLetters() {
        return missingLetters;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void addCharNotPlace(String ch) {
        charNotPlace.add(ch);
    }

    public void removeCharNotPlace(String ch) {
        charNotPlace.remove(ch);
    }

    public void addMissingLetters(String ch) {
        missingLetters.add(ch.charAt(0));
    }

    private ArrayList isAnswerInitialized() {
        ArrayList<String> answer = new ArrayList<>();
        for (int i = 0; i < Game.NUMBER_OF_LETTERS; i++) {
            answer.add(i, " ");
        }
        return answer;
    }

    public void addListStorage(List<Attempt> attemptsList, Game game) {
        Attempt lastAttempt = attemptsList.get(attemptsList.size() - 1);
        if (answer.isEmpty()) {
            this.answer = isAnswerInitialized();
        }

        for (int i = 0; i < lastAttempt.getLetters().size(); i++) {

            String ch = String.valueOf(lastAttempt.getLetters().get(i).getValue());
            Letter.LetterStatus status = lastAttempt.getLetters().get(i).getStatus();

            if (status == Letter.LetterStatus.IN_PLACE) {
                answer.set(i, ch);
                continue;
            }

            if ((status == Letter.LetterStatus.NOT_PLACE)) {
                addCharNotPlace(ch);

                if ((game.countingChar(game.getHiddenWord(), ch.charAt(0))) == game.countingChar(String.valueOf(getAnswer()), ch.charAt(0))) {
                    removeCharNotPlace(ch);
                }
                continue;
            }

            else {
                addMissingLetters(ch);
            }
        }
    }
}
