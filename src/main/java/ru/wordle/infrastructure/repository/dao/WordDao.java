package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.wordle.infrastructure.entity.WordEntity;

import java.util.UUID;

public interface WordDao extends JpaRepository<WordEntity, UUID> {
    long countByWord(String word);

    @Query(value = "SELECT word FROM words ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    String findRandomWord();
}
