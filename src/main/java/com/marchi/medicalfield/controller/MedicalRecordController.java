package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.MedicalRecord;
import com.marchi.medicalfield.domain.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @PostMapping
    public MedicalRecord create( @RequestBody MedicalRecord medicalRecord )
    {
        return medicalRecordRepository.save( medicalRecord );
    }
}