package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import ru.wordle.domain.model.LetterStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "letters")
@Getter
@Setter
public class LetterEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "letter", nullable = false, length = 1)
    private String letter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private LetterStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", referencedColumnName = "id", nullable = false)
    private AttemptEntity attempt;
}
