package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.infrastructure.repository.WordRepository; // ИСПРАВЛЕННЫЙ ИМПОРТ
import ru.wordle.infrastructure.repository.dao.WordDao;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
@Profile("jpa")
public class WordRepositoryJpa implements WordRepository {

    private static final Logger log = LoggerFactory.getLogger(WordRepositoryJpa.class);
    private final WordDao wordDao;

    @PostConstruct
    public void init() {
        log.error("==========================================");
        log.error(">>> JPA REPOSITORY CREATED! (Hibernate) <<<");
        log.error("==========================================");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExists(String word) {
        log.info("Checking word existence via JPA: {}", word);
        return wordDao.countByWord(word) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public String getRandomWord() {
        log.info("Getting random word via JPA");
        return wordDao.findRandomWord();
    }
}
