package ru.wordle.infrastructure.entity;

import javax.persistence.*;  // javax - для твоего проекта

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
