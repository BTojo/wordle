package ru.wordle.infrastructure.initializer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.wordle.domain.entity.Dictionary;
import ru.wordle.domain.entity.DictionaryRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DictionaryLoader {

    private final DictionaryRepository repo;

    public DictionaryLoader(DictionaryRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void load() {
        try {
            ClassPathResource res = new ClassPathResource("wordle.txt");
            if (!res.exists()) {
                System.out.println("[DICT] wordle.txt not found in classpath, skip");
                return;
            }
            int added = 0, skipped = 0;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(res.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String w = line.trim().toLowerCase();
                    if (!StringUtils.hasText(w)) continue;
                    if (w.length() > 64) continue; // защитимся от слишком длинных слов
                    try {
                        if (repo.existsByWord(w)) {
                            skipped++;
                            continue;
                        }
                        repo.save(new Dictionary(w));
                        added++;
                    } catch (Exception e) {
                        // На случай гонки по уникальному индексу (редко), просто пропустим
                        skipped++;
                    }
                }
            }
            System.out.println("[DICT] loaded: added=" + added + ", skipped=" + skipped);
        } catch (Exception e) {
            System.out.println("[DICT] ERROR: " + e.getMessage());
        }
    }
}
