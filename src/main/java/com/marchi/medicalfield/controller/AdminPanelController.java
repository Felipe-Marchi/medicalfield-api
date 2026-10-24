package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/medicalfield" )
public class AdminPanelController
{
    
    @Autowired
    private ProfessionalRepository professionalRepository;
    
    @GetMapping
    public String dashboard( Model model )
    {
        model.addAttribute( "titulo", "Painel Administrativo - MedicalField" );
        return "dashboard";
    }
    
    @GetMapping( "/professionals" )
    public String listProfessionals( Model model )
    {
        model.addAttribute( "professionals", professionalRepository.findAll() );
        return "professionals";
    }
}