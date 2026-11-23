package ru.wordle.infrastructure.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.wordle.domain.event.EventSender;
import ru.wordle.domain.model.GameFinishedEvent;

@Component
@ConditionalOnProperty(
        name = "kafka.topics.game-finished.producer-enabled",
        havingValue = "false"
)
public class NoOpEventSender implements EventSender {

    @Override
    public void send(GameFinishedEvent event) {
    }
}