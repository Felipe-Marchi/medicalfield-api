package com.marchi.medicalfield.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicalfield")
public class AdminPanelController {
    
    @GetMapping
    public String dashboard(Model model) {
        
        model.addAttribute("titulo", "Painel Administrativo - MedicalField");
        
        return "dashboard";
    }
}