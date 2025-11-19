package ru.wordle.infrastructure.initializer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
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
    private final TransactionTemplate transactionTemplate;

    public DictionaryLoader(DictionaryRepository repo, TransactionTemplate transactionTemplate) {
        this.repo = repo;
        this.transactionTemplate = transactionTemplate;
    }

    @PostConstruct
    public void load() {
        System.out.println("[DICT] Starting dictionary load...");

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
                    if (w.length() > 64) continue;

                    try {
                        Boolean exists = transactionTemplate.execute(status ->
                                repo.existsByWord(w)
                        );

                        if (Boolean.TRUE.equals(exists)) {
                            skipped++;
                            continue;
                        }

                        transactionTemplate.execute(status -> {
                            repo.save(new Dictionary(w));
                            return null;
                        });

                        added++;
                    } catch (Exception e) {
                        System.out.println("[DICT] Error saving word '" + w + "': " + e.getClass().getSimpleName());
                        skipped++;
                    }
                }
            }
            System.out.println("[DICT] Final result: added=" + added + ", skipped=" + skipped);
        } catch (Exception e) {
            System.out.println("[DICT] FATAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}