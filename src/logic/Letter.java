package logic;

public class Letter {

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    public LetterStatus getStatus(LetterStatus inPlace) {
        return status;
    }

    public void setStatus(LetterStatus status) {
        this.status = status;
    }

    private Character value;

    private LetterStatus status;


//    public Letter(Character character, LetterStatus status) {
//        this.value = character;
//        this.status = status;
//    }

    @Override
    public String toString() {
        return "{" + value + " = " + status + "}";
    }
}

enum LetterStatus {
        IN_PLACE,
        NOT_PLACE,
        MISSING }

