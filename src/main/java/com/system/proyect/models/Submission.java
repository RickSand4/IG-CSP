package com.system.proyect.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Submission {
    private Long id;
    private Long challengeId;
    private String code;
    private Boolean isCorrect;
    private LocalDateTime submittedAt;
}