package ru.wordle.api.controller;  // пакет для REST-контроллеров [web:725]

import org.springframework.beans.factory.annotation.Autowired;  // DI для внедрения бинов [web:725]
import org.springframework.jdbc.core.JdbcTemplate;  // выполнение SQL [web:725]
import org.springframework.web.bind.annotation.GetMapping;  // GET-обработчик [web:725]
import org.springframework.web.bind.annotation.RequestMapping;  // базовый путь [web:725]
import org.springframework.web.bind.annotation.RestController;  // REST-контроллер [web:725]

@RestController  // отдаёт строки/JSON вместо HTML [web:725]
@RequestMapping("/api/db")  // общий префикс для эндпоинтов диагностики БД [web:725]
public class DatabaseTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;  // внедряется из DatabaseConfig [web:725]

    @GetMapping("/ping")  // GET /api/db/ping [web:725]
    public String ping() {
        try {
            Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);  // простой тест запроса [web:725]
            return " PostgreSQL OK, SELECT 1 = " + one;  // успех подключения и выполнения SQL [web:725]
        } catch (Exception e) {
            return " Ошибка подключения к PostgreSQL: " + e.getMessage();  // покажет причину (порт, пароль, база) [web:725]
        }
    }
}
