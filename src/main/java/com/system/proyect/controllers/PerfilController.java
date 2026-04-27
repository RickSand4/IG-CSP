package com.system.proyect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class PerfilController {
    @GetMapping
    public String viewProfile() { return "profile/view"; }
    @GetMapping("/edit")
    public String editProfile() { return "profile/edit"; }
}