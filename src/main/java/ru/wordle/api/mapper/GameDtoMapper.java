package ru.wordle.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wordle.api.dto.GameDto;
import ru.wordle.domain.model.Game;

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
        dto.setStatus(game.getGameStatus());
        dto.setCreatedAt(game.getCreatedAt());

//        dto.setAttempts(
//                game.getAttemptsList()
//                        .stream()
//                        .map(attemptDtoMapper::toDto)
//                        .toList()
//        );

        return dto;
    }
}
