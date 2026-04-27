package com.system.proyect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {
    @GetMapping("/search-competitions")
    public String searchCompetitions() { return "estudiante/search-competitions"; }
    @GetMapping("/rankings")
    public String rankings() { return "estudiante/rankings"; }
    @GetMapping("/my-enrollments")
    public String myEnrollments() { return "estudiante/my-enrollments"; }
}