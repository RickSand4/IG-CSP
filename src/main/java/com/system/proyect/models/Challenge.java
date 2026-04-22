package com.system.proyect.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    private Long id;
    private String title;
    private String description;
    private String example;
    private String difficulty;
    private Integer sectionOrder;
    private Integer timeLimitSeconds;
}

