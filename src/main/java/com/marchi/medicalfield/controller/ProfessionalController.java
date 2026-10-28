package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.Professional;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public List<Professional> listAll()
    {
        return professionalRepository.findAll();
    }
    
    @GetMapping( "/{id}" )
    public ResponseEntity<Professional> getById( @PathVariable Long id )
    {
        return professionalRepository.findById( id )
                                     .map( professional -> ResponseEntity.ok( professional ) )
                                     .orElse( ResponseEntity.notFound()
                                                            .build() );
    }
    
    @PostMapping
    public Professional create( @RequestBody Professional professional )
    {
        String encryptPassword = passwordEncoder.encode( professional.getPasswordHash() );
        professional.setPasswordHash( encryptPassword );
        
        return professionalRepository.save( professional );
    }
}