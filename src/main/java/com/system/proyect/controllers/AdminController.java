package com.system.proyect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/manage-users")
    public String manageUsers() { return "admin/manage-users"; }
    @GetMapping("/manage-competitions")
    public String manageCompetitions() { return "admin/manage-competitions"; }
    @GetMapping("/manage-problems")
    public String manageProblems() { return "admin/manage-problems"; }
    @GetMapping("/manage-testcases")
    public String manageTestcases() { return "admin/manage-testcases"; }
}