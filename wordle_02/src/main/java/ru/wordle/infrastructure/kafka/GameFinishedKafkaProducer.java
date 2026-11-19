package ru.wordle.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.wordle.domain.event.*;
import ru.wordle.domain.model.GameFinishedEvent;
import ru.wordle.infrastructure.kafka.converter.GameFinishedEventConverter;
import ru.wordle.infrastructure.kafka.dto.GameFinishedEventDto;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class GameFinishedKafkaProducer implements EventSender {

    private final KafkaTemplate<String, GameFinishedEventDto> kafkaTemplate;
    private final GameFinishedEventConverter converter;

    @Value("${kafka.topics.game-finished}")
    private String topic;

    @Override
    public void send(GameFinishedEvent event) {
        GameFinishedEventDto eventDto = converter.convert(event);
        kafkaTemplate.send(topic, event.getGame().getGameId(), eventDto);
    }
}