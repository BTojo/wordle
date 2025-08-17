package ru.wordle;

import ru.wordle.datastorage.StorageException;
import ru.wordle.presentation.OutputToConsole;
import ru.wordle.presentation.Storage;
import ru.wordle.presentation.WordleView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws StorageException {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            WordleView wordleView = context.getBean(WordleView.class);

           String[] beanNames = context.getBeanDefinitionNames();
            System.out.println("\nБины: ");
            for (String name : beanNames) {
                System.out.println(name);
            }
            System.out.println();

            wordleView.start();

        }
    }
}