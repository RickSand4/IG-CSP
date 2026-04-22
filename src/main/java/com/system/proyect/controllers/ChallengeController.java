package com.system.proyect.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    // Datos simulados
    private static final List<Challenge> challenges = new ArrayList<>();

    static {
        challenges.add(new Challenge(1L, "Variables", "Declara una variable 'puntos' con valor 100.", "int puntos = 100;", "EASY", 1, 600));
        challenges.add(new Challenge(2L, "Condicionales", "Determina si un número es par o impar.", "Entrada 5 → 'impar'", "MEDIUM", 1, 600));
        challenges.add(new Challenge(3L, "Bucles", "Imprime los primeros 10 números de Fibonacci.", "0,1,1,2,3,5,8,13,21,34", "MEDIUM", 2, 720));
        challenges.add(new Challenge(4L, "Funciones", "Crea una función que sume dos números.", "suma(3,4) → 7", "EASY", 2, 720));
        challenges.add(new Challenge(5L, "Arreglos", "Encuentra el número mayor en un arreglo.", "[2,8,1,5] → 8", "HARD", 3, 900));
    }

    @GetMapping
    public List<Challenge> getAll() {
        return challenges;
    }

    @GetMapping("/section/{section}")
    public List<Challenge> getBySection(@PathVariable int section) {
        return challenges.stream()
                .filter(c -> c.getSectionOrder() == section)
                .toList();
    }

    @GetMapping("/{id}")
    public Challenge getById(@PathVariable Long id) {
        return challenges.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Clase interna (o puedes ponerla en archivo aparte)
    static class Challenge {
        private Long id;
        private String title;
        private String description;
        private String example;
        private String difficulty;
        private int sectionOrder;
        private int timeLimitSeconds;

        public Challenge(Long id, String title, String description, String example, String difficulty, int sectionOrder, int timeLimitSeconds) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.example = example;
            this.difficulty = difficulty;
            this.sectionOrder = sectionOrder;
            this.timeLimitSeconds = timeLimitSeconds;
        }

        // Getters y setters (necesarios para serialización JSON)
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getExample() { return example; }
        public void setExample(String example) { this.example = example; }
        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
        public int getSectionOrder() { return sectionOrder; }
        public void setSectionOrder(int sectionOrder) { this.sectionOrder = sectionOrder; }
        public int getTimeLimitSeconds() { return timeLimitSeconds; }
        public void setTimeLimitSeconds(int timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }
    }
}