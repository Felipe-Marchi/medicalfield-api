package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.dto.LoginRequestDTO;
import com.marchi.medicalfield.domain.model.Professional;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    // Endpoint de login para validacao do socorrista via usuario e senha
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        if (dto.getUsername() == null || dto.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário e senha são obrigatórios.");
        }

        Optional<Professional> professionalOpt = professionalRepository.findByUsername(dto.getUsername());

        if (professionalOpt.isPresent()) {
            Professional professional = professionalOpt.get();
            if (passwordEncoder.matches(dto.getPassword(), professional.getPasswordHash()) && Boolean.TRUE.equals(professional.getActive())) {
                return ResponseEntity.ok(professional);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos.");
    }
}