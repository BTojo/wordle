package ru.wordle.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Letter {

    private Character value;

    private LetterStatus status;

    private Integer position;

}