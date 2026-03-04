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
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
public class GameEntity implements Persistable<UUID> {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Transient
    private boolean isNew;

    @Column(name = "secret_word", nullable = false, length = 5)
    private String secretWord;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private GameStatus status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @OneToMany(
            mappedBy = "game",
            fetch = FetchType.LAZY
    )
    private List<AttemptEntity> attemptEntities;

    public boolean isAttemptsInitialized() {
        return Hibernate.isInitialized(attemptEntities) && !CollectionUtils.isEmpty(attemptEntities);
    }
}
