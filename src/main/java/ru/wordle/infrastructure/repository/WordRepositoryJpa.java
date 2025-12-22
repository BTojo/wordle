package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.infrastructure.repository.WordRepository;
import ru.wordle.infrastructure.repository.dao.WordDao;
import ru.wordle.infrastructure.entity.WordEntity;
import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@RequiredArgsConstructor
@Profile("jpa")
@Slf4j
public class WordRepositoryJpa implements WordRepository {

    private final WordDao wordDao;

    @PostConstruct
    public void init() {
        log.info(">>> JPA REPOSITORY CREATED! <<<");
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExists(String word) {
        return wordDao.countByWord(word) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public String getRandomWord() {
        log.info("Getting random word via JPA (Optimized)");

        long count = wordDao.count();
        if (count == 0) {
            throw new IllegalStateException("Database is empty!");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt((int) count);

        Page<WordEntity> wordPage = wordDao.findAll(PageRequest.of(randomIndex, 1));

        if (wordPage.hasContent()) {
            return wordPage.getContent().get(0).getWord();
        }

        return null;
    }
}
