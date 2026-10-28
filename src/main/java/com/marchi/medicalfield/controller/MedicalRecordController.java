package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.MedicalRecord;
import com.marchi.medicalfield.domain.repository.MedicalRecordRepository;
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
@RequestMapping( "/medical-records" )
public class MedicalRecordController
{
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    
    @GetMapping
    public List<MedicalRecord> listAll()
    {
        return medicalRecordRepository.findAll();
    }
    
    @GetMapping( "/{cpf}" )
    public ResponseEntity<MedicalRecord> getByPatientCpf( @PathVariable String cpf )
    {
        return medicalRecordRepository.findById( cpf )
                                      .map( record -> ResponseEntity.ok( record ) ) // Retorna 200 OK se encontrar
                                      .orElse( ResponseEntity.notFound()
                                                             .build() ); // Retorna 404 Not Found se não existir
    }
    
    @PostMapping
    public MedicalRecord create( @RequestBody MedicalRecord medicalRecord )
    {
        return medicalRecordRepository.save( medicalRecord );
    }
}