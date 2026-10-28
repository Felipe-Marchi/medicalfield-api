package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.Medication;
import com.marchi.medicalfield.domain.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {
    
    @Autowired
    private MedicationRepository medicationRepository;
    
    @GetMapping
    public List<Medication> listAll()
    {
        return medicationRepository.findAll();
    }
    
    @GetMapping( "/{id}" )
    public ResponseEntity<Medication> getById( @PathVariable Long id )
    {
        return medicationRepository.findById( id )
                                   .map( medication -> ResponseEntity.ok( medication ) )
                                   .orElse( ResponseEntity.notFound()
                                                          .build() );
    }
    
    @PostMapping
    public Medication create( @RequestBody Medication medication )
    {
        return medicationRepository.save( medication );
    }
}