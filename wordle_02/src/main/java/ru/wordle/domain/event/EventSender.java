package ru.wordle.domain.event;

import ru.wordle.domain.model.GameFinishedEvent;

public interface EventSender {

    void send(GameFinishedEvent event);
}