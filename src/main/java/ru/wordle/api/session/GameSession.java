package ru.wordle.api.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.wordle.domain.model.Game;

@Component
@SessionScope
public class GameSession {
    private Game game;

    public Game get() {
        return game;
    }

    public void set(Game game) {
        this.game = game;
    }

    public void clear() {
        this.game = null;
    }
}
