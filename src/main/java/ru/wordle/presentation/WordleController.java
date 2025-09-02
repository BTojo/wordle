package ru.wordle.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.wordle.logic.Game;
import ru.wordle.logic.GameFactory;
import ru.wordle.logic.GameStatus; // Добавлен импорт
import ru.wordle.datastorage.StorageException;

import jakarta.servlet.http.HttpSession;

@Controller
public class WordleController {

    @Autowired
    private GameFactory gameFactory;

    @GetMapping
    public ModelAndView home(HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        if (game == null) {
            game = gameFactory.create(); // Убрали try-catch
            session.setAttribute("game", game);
            System.out.println("New game created: " + game.hashCode());
        }
        ModelAndView mav = new ModelAndView("game");
        mav.addObject("status", "Enter a word to start the game...");
        return mav;
    }

    @PostMapping
    public ModelAndView guess(@RequestParam String guess, HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        ModelAndView mav = new ModelAndView("game");
        if (game != null) {
            if (game.validateWord(guess) && gameFactory.getStorage().isExists(guess)) {
                game.makeAttempt(guess);
                String status = "Status: " + game.getGameStatus() + ". Attempts: " + game.getAttemptsList().size();
                mav.addObject("status", status);
            } else {
                mav.addObject("status", "Invalid word!");
            }
            if (game.getGameStatus() == GameStatus.WIN) { // Изменено с Game.GameStatus.WIN
                mav.addObject("status", "You win!");
            } else if (game.getGameStatus() == GameStatus.LOSE) { // Изменено с Game.GameStatus.LOSE
                mav.addObject("status", "You lose! Word was " + game.getHiddenWord());
            }
        } else {
            mav.addObject("status", "Game not started. Please refresh the page.");
        }
        return mav;
    }
}