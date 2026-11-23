package ru.wordle.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.wordle.infrastructure.kafka.dto.GameFinishedEventDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(
        name = "kafka.topics.game-finished.consumer-enabled",
        havingValue = "true"
)

@Slf4j
@Component
public class GameFinishedKafkaConsumer {
    @KafkaListener(
            topics = "${kafka.topics.game-finished.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(GameFinishedEventDto event) {
        log.info("Получено событие о завершении игры: {}", event);
    }
}