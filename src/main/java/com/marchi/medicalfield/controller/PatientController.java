package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.Patient;
import com.marchi.medicalfield.domain.repository.PatientRepository;
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
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;
    
    @GetMapping
    public List<Patient> listAll()
    {
        return patientRepository.findAll();
    }
    
    @GetMapping( "/{cpf}" )
    public ResponseEntity<Patient> getByCpf( @PathVariable String cpf )
    {
        return patientRepository.findById( cpf )
                                .map( patient -> ResponseEntity.ok( patient ) )
                                .orElse( ResponseEntity.notFound()
                                                       .build() );
    }
    
    @PostMapping
    public Patient create( @RequestBody Patient patient )
    {
        return patientRepository.save( patient );
    }
}