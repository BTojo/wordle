package ru.wordle.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
@Profile("jdbc")
public class WordRepositoryJdbc implements WordRepository {
    @PostConstruct
    public void init() {
        System.out.println("JDBC CREATED! ");
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean isExists(String word) {
        String sql = "SELECT COUNT(*) FROM words WHERE word = :word";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("word", word);

        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);

        return count != null && count > 0;
    }

    @Override
    public String getRandomWord() {
        String sql = "SELECT word FROM words ORDER BY RANDOM() LIMIT 1";

        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), String.class);
    }
}
