package ru.wordle.infrastructure.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.wordle.domain.model.GameStatus;  // Импортируем enum из domain модели

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
public class GameEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "secret_word", nullable = false, length = 5)
    private String secretWord;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private GameStatus status;  // ИЗМЕНЕНО: GameStatus вместо String

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}