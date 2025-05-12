package logic;

public class Letter {

    private Character value;
    private LetterStatus status;


    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    public LetterStatus getStatus() {
        return status;
    }

    public void setStatus(LetterStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" + value + " = " + status + "}";
    }


    public enum LetterStatus {
        IN_PLACE,
        NOT_PLACE,
        MISSING
    }
}

