package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.infrastructure.repository.dao.WordDao;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
@Profile("jpa")
public class WordRepositoryJpa implements WordRepository {
    @PostConstruct
    public void init() {
        System.out.println("JPA CREATED! ");
    }

    private final WordDao wordDao;

    @Override
    @Transactional(readOnly = true)
    public boolean isExists(String word) {
        return wordDao.countByWord(word) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public String getRandomWord() {
        return wordDao.findRandomWord();
    }
}
