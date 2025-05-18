package logic;

import java.util.List;

public class Attempt {

    private List<Letter> letters;

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    @Override
    public String toString() {
        return "Attempt {{value=" + letters.toString() + "}}";
    }
}