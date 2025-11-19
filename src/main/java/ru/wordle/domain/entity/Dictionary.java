package ru.wordle.domain.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "dictionary", uniqueConstraints = {
        @UniqueConstraint(name = "uk_dictionary_word", columnNames = "word")
})
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String word;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Dictionary() {
    }

    public Dictionary(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
