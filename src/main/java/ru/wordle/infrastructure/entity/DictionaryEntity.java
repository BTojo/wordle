package ru.wordle.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dictionary", uniqueConstraints = {
        @UniqueConstraint(name = "uk_dictionary_word", columnNames = "word")
})
@Getter
@Setter
@NoArgsConstructor
public class DictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String word;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DictionaryEntity(String word) {
        this.word = word;
    }
}
