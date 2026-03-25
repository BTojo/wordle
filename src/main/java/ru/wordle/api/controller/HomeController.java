package ru.wordle.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
                <html>
                <head><title>Wordle Game API</title></head>
                <body>
                    <h1>Wordle Game API</h1>
                    <p>Available endpoints:</p>
                    <ul>
                        <li><strong>POST</strong> /api/games - Start new game</li>
                        <li><strong>GET</strong> /api/games - Get current game state</li>
                        <li><strong>POST</strong> /api/games/guess - Submit guess: {"guess": "APPLE"}</li>
                        <li><strong>POST</strong> /api/users/registration - Submit guess: {"login": "test", "password": "password"}</li>
                        <li><strong>POST</strong> api/users/login - Submit guess: {"login": "test", "password": "password"}</li>
                    </ul>
                    <p>Example: POST http://localhost:8082/wordle_02/api/games/guess</p>
                </body>
                </html>
                """;
    }
}