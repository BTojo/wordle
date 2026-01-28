package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "attempts")
@Getter
@Setter
public class AttemptEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "attempt_number", nullable = false)
    private int attemptNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false)
    private GameEntity game;

    @OneToMany(
            mappedBy = "attempt",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LetterEntity> letters;
}
