package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;
import ru.wordle.domain.model.LetterStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "letters")
@Getter
@Setter
public class LetterEntity implements Persistable<UUID> {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "letter", nullable = false, length = 1)
    private String letter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LetterStatus status;

    @Column(name = "position", nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private AttemptEntity attempt;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}