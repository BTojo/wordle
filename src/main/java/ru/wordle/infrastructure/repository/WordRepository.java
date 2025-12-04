package ru.wordle.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.wordle.infrastructure.entity.WordEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class WordRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public boolean existsByWord(String word) {
        Long count = em.createQuery(
                        "select count(d) from DictionaryEntity d where d.word = :w", Long.class)
                .setParameter("w", word)
                .getSingleResult();
        return count > 0;
    }

    @Transactional
    public void save(WordEntity d) {
        em.persist(d);
    }

    @Transactional(readOnly = true)
    public long count() {
        return em.createQuery("select count(d) from DictionaryEntity d", Long.class)
                .getSingleResult();
    }
}
