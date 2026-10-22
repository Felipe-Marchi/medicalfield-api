package com.marchi.medicalfield.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {
    
    @GetMapping
    public String dashboard(Model model) {
        // Envia uma variável chamada "titulo" para o HTML
        model.addAttribute("titulo", "Painel Administrativo - MedicalField");
        
        // Retorna o nome do arquivo HTML que o Spring deve procurar (sem a extensão .html)
        return "dashboard";
    }
}