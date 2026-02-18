package ru.wordle.domain.model;

public class Letter {

    private Character value;
    private LetterStatus status;
    private Integer position;  // Добавлено поле для позиции

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "{" + value + " = " + status + "}";
    }
}