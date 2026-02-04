package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;



import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "attempts")
@Getter
@Setter
public class AttemptEntity implements Persistable<UUID> {

    private List<LetterEntity> letters;

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "attempt_number", nullable = false)
    private int attemptNumber;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "game_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    private GameEntity game;

    @OneToMany(
            mappedBy = "attempt",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}