package ru.wordle.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.api.dto.GameDto;
import ru.wordle.domain.model.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class GameDtoMapper {

    private final AttemptDtoMapper attemptDtoMapper;

    public GameDto toDto(Game game) {
        if (game == null) {
            return null;
        }

        GameDto dto = new GameDto();
        dto.setGameId(game.getGameId());
        dto.setGameStatus(game.getGameStatus());
        dto.setCreateDateTime(game.getCreatedAt());

        if (game.getGameStatus() == GameStatus.GAME_WIN
                || game.getGameStatus() == GameStatus.GAME_LOSING) {
            dto.setTargetWord(game.getSecretWord());
        }

        if (game.getAttemptsList() != null && !game.getAttemptsList().isEmpty()) {
            dto.setAttempts(
                    game.getAttemptsList()
                            .stream()
                            .map(attemptDtoMapper::toDto)
                            .toList()
            );
        } else {
            dto.setAttempts(Collections.emptyList());
        }

        return dto;
    }
}