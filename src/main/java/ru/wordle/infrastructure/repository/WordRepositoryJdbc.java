package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.wordle.domain.repository.WordRepository;
import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@RequiredArgsConstructor
@Profile("jdbc")
@Slf4j
public class WordRepositoryJdbc implements WordRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        log.info(">>> JDBC REPOSITORY CREATED! <<<");
    }

    @Override
    public boolean isExists(String word) {
        String sql = "SELECT COUNT(*) FROM words WHERE word = :word";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("word", word);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public String getRandomWord() {
        log.info("Getting random word via JDBC (Optimized)");

        String countSql = "SELECT COUNT(*) FROM words";
        Integer count = jdbcTemplate.queryForObject(countSql, new MapSqlParameterSource(), Integer.class);

        if (count == null || count == 0) {
            throw new IllegalStateException("Database is empty!");
        }

        int offset = ThreadLocalRandom.current().nextInt(count);

        String fetchSql = "SELECT word FROM words LIMIT 1 OFFSET :offset";

        return jdbcTemplate.queryForObject(
                fetchSql,
                new MapSqlParameterSource("offset", offset),
                String.class
        );
    }
}
