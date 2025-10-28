package ru.wordle.api.mapper;

import org.springframework.stereotype.Component;
import ru.wordle.api.dto.GameDto;
import ru.wordle.api.dto.LetterDto;
import ru.wordle.domain.model.Game;

import java.util.List;

@Component
public class GameMapper {
    public GameDto toDto(Game game) {
        List<List<LetterDto>> attempts = game.getAttemptsList().stream()
                .map(a -> a.getLetters().stream()
                        .map(l -> new LetterDto(l.getValue(), l.getStatus().name()))
                        .toList())
                .toList();

        return new GameDto(game.getGameStatus(), attempts);
    }
}
