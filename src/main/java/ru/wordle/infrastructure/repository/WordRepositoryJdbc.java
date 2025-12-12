package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.wordle.infrastructure.repository.WordRepository; // ИСПРАВЛЕННЫЙ ИМПОРТ

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
@Profile("jdbc")
public class WordRepositoryJdbc implements WordRepository {

    private static final Logger log = LoggerFactory.getLogger(WordRepositoryJdbc.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        log.error("==========================================");
        log.error(">>> JDBC REPOSITORY CREATED! (Native SQL) <<<");
        log.error("==========================================");
    }

    @Override
    public boolean isExists(String word) {
        log.info("Checking word existence via JDBC: {}", word);
        String sql = "SELECT COUNT(*) FROM words WHERE word = :word";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("word", word);

        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public String getRandomWord() {
        log.info("Getting random word via JDBC");
        String sql = "SELECT word FROM words ORDER BY RANDOM() LIMIT 1";

        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), String.class);
    }
}
