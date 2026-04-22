package com.system.proyect.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @PostMapping
    public ResponseEntity<SubmissionResult> submit(@RequestBody Submission submission) {
        // Simulación: si el código contiene "return" lo damos por válido
        boolean correct = submission.getCode() != null && submission.getCode().contains("return");
        SubmissionResult result = new SubmissionResult();
        result.setCorrect(correct);
        result.setMessage(correct ? "✅ ¡Correcto!" : "❌ Intenta de nuevo");
        result.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(result);
    }

    static class Submission {
        private Long challengeId;
        private String code;
        // getters y setters
        public Long getChallengeId() { return challengeId; }
        public void setChallengeId(Long challengeId) { this.challengeId = challengeId; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }

    static class SubmissionResult {
        private boolean correct;
        private String message;
        private LocalDateTime timestamp;
        // getters y setters
        public boolean isCorrect() { return correct; }
        public void setCorrect(boolean correct) { this.correct = correct; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}