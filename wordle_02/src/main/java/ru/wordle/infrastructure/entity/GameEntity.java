package ru.wordle.infrastructure.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import ru.wordle.domain.model.GameStatus;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;
import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
public class GameEntity implements Persistable<UUID> {

    @Id
    @Column(name = "game_id", columnDefinition = "UUID")
    private UUID id;

    @Transient
    private boolean isNew;

    @Column(name = "secret_word", length = 5)
    private String secretWord;

    @Column(name = "owner_id")
    private String ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private GameStatus status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @OneToMany(
            mappedBy = "game",
            fetch = FetchType.LAZY
    )
    private List<AttemptEntity> attemptEntities;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public boolean isAttemptsInitialized() {
        return Hibernate.isInitialized(attemptEntities)
                && !CollectionUtils.isEmpty(attemptEntities);
    }
}