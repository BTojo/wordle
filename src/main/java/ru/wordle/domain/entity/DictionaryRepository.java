package ru.wordle.domain.entity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DictionaryRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public boolean existsByWord(String word) {
        Long count = em.createQuery(
                        "select count(d) from Dictionary d where d.word = :w", Long.class)
                .setParameter("w", word)
                .getSingleResult();
        return count != 0;
    }

    @Transactional
    public void save(Dictionary d) {
        em.persist(d);
    }
}