package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.repository.AttendanceRepository;
import com.marchi.medicalfield.domain.repository.MedicalRecordRepository;
import com.marchi.medicalfield.domain.repository.MedicationRepository;
import com.marchi.medicalfield.domain.repository.PatientRepository;
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
    
    @Autowired
    private MedicationRepository medicationRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @GetMapping
    public String dashboard( Model model )
    {
        model.addAttribute( "titulo", "Painel Administrativo - MedicalField" );
        model.addAttribute( "attendances", attendanceRepository.findAllDashboardData() );
        return "dashboard";
    }
    
    @GetMapping( "/professionals" )
    public String listProfessionals( Model model )
    {
        model.addAttribute( "professionals", professionalRepository.findAll() );
        return "professionals";
    }
    
    @GetMapping( "/medications" )
    public String listMedications( Model model )
    {
        model.addAttribute( "medications", medicationRepository.findAll() );
        return "medications";
    }
    
    @GetMapping( "/patients" )
    public String listPatients( Model model )
    {
        model.addAttribute( "patients", patientRepository.findAll() );
        return "patients";
    }
}