package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "words", uniqueConstraints = {
        @UniqueConstraint(name = "uk_word_text", columnNames = "word")
})
@Getter
@Setter
@NoArgsConstructor
public class WordEntity {

    @Id
    @Column(name = "word_id", nullable = false)
    private UUID wordId;

    @Column(nullable = false, length = 5)
    private String word;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public WordEntity(String word) {
        this.word = word;
    }

    @PrePersist
    public void generateId() {
        if (wordId == null) {
            wordId = UUID.randomUUID();
        }
    }
}
