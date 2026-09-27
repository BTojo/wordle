package ru.wordle.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.wordle.domain.model.GameFinishedEvent;

@Component
@RequiredArgsConstructor
public class GameFinishedKafkaProducer {

    private static final String TOPIC = "wordle-game-finished";

    private final KafkaTemplate<String, GameFinishedEvent> kafkaTemplate;

    public void send(GameFinishedEvent event) {
        kafkaTemplate.send(TOPIC, event.getGameId(), event);
    }
}