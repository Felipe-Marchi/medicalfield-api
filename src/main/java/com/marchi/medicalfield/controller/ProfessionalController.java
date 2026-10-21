package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.Professional;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {

    @Autowired
    private ProfessionalRepository professionalRepository;
    
    @GetMapping
    public List<Professional> listAll() {
        return professionalRepository.findAll();
    }
    
    @PostMapping
    public Professional create(@RequestBody Professional professional) {
        return professionalRepository.save(professional);
    }
}