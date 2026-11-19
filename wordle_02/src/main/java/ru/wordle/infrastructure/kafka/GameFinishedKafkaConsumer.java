package ru.wordle.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
//import ru.wordle.domain.model.GameFinishedEvent;
import ru.wordle.infrastructure.kafka.dto.GameFinishedEventDto;

@Slf4j
@Component
public class GameFinishedKafkaConsumer {
    @KafkaListener(
            topics = "${kafka.topics.game-finished}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(GameFinishedEventDto event) {
        log.info("Получено событие о завершении игры: {}", event);
    }
}