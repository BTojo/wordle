package ru.wordle.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.wordle.domain.model.GameFinishedEvent;

@Slf4j
@Component
public class GameFinishedKafkaConsumer {

    @KafkaListener(
            topics = "wordle-game-finished",
            groupId = "wordle-game-finished-consumer"
    )
    public void consume(GameFinishedEvent event) {
        log.info("Получено событие о завершении игры: {}", event);
    }
}