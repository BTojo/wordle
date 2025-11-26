package ru.wordle.infrastructure.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.wordle.infrastructure.entity.DictionaryEntity;
import ru.wordle.infrastructure.repository.DictionaryRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class DictionaryLoader {

    private final DictionaryRepository repo;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional
    public void load() {
        log.info("[DICT] Starting dictionary load...");

        if (repo.count() > 0) {
            log.info("[DICT] Dictionary already loaded (count > 0). Skip.");
            return;
        }

        try {
            ClassPathResource res = new ClassPathResource("wordle.txt"); // Или dictionary.txt

            if (!res.exists()) {
                log.error("[DICT] File not found!");
                return;
            }

            int added = 0;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(res.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                while ((line = br.readLine()) != null) {
                    String w = line.trim().toLowerCase();

                    if (!StringUtils.hasText(w) || w.length() > 5 || repo.existsByWord(w)) {
                        continue;
                    }

                    repo.save(new DictionaryEntity(w));
                    added++;
                }
            }
            log.info("[DICT] Final result: added={}", added);

        } catch (IOException e) {
            log.error("[DICT] FATAL ERROR: ", e);
        }
    }

}

