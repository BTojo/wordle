package ru.wordle.infrastructure.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wordle.infrastructure.entity.WordEntity;

import java.util.UUID;

@Repository
public interface WordDao extends JpaRepository<WordEntity, UUID> {

    long countByWord(String word);

}
