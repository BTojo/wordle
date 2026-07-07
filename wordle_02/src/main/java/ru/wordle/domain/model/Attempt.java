package ru.wordle.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Attempt {

    private String id;
    private String gameId;
    private int attemptNumber;
    private List<Letter> letters = new ArrayList<>();
}
