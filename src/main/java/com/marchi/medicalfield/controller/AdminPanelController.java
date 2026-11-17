package com.marchi.medicalfield.controller;

import java.time.LocalDateTime;

import com.marchi.medicalfield.domain.dto.AttendanceDashboardDTO;
import com.marchi.medicalfield.domain.repository.AttendanceRepository;
import com.marchi.medicalfield.domain.repository.MedicalRecordRepository;
import com.marchi.medicalfield.domain.repository.MedicationRepository;
import com.marchi.medicalfield.domain.repository.PatientRepository;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import com.marchi.medicalfield.domain.repository.SystemLogRepository;
import com.marchi.medicalfield.service.PdfEmailIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private SystemLogRepository systemLogRepository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private PdfEmailIntegrationService pdfEmailService;
    
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
    
    @GetMapping( "/logs" )
    public String viewLogs( Model model )
    {
        model.addAttribute( "logs", systemLogRepository.findAllByOrderByTimestampDesc() );
        return "logs";
    }

    @GetMapping( "/architecture" )
    public String viewArchitecture( Model model )
    {
        model.addAttribute( "titulo", "Arquitetura do Sistema - MedicalField" );
        return "architecture";
    }
    
    // --- ROTA DO PDF ---
    @GetMapping( "/attendances/pdf" )
    public ResponseEntity<byte[]> downloadPdf(
            @RequestParam String cpf,
            @RequestParam @DateTimeFormat( iso = DateTimeFormat.ISO.DATE_TIME ) LocalDateTime datetime )
    {
        // 1. Busca o DTO pronto com todos os dados
        AttendanceDashboardDTO dto = attendanceRepository.findDashboardDataById( cpf, datetime )
                                                         .orElseThrow( () -> new IllegalArgumentException( "Atendimento não encontrado." ) );
        
        // 2. Chama o Node.js passando o DTO inteiro
        byte[] pdfBytes = pdfEmailService.generatePdf( dto );
        
        // 3. Configura o download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_PDF );
        headers.setContentDispositionFormData( "attachment", "ficha_atendimento.pdf" );
        
        return new ResponseEntity<>( pdfBytes, headers, HttpStatus.OK );
    }
    
    // --- ROTA DO E-MAIL ---
    @GetMapping( "/attendances/email" )
    public String sendEmail(
            @RequestParam String cpf,
            @RequestParam @DateTimeFormat( iso = DateTimeFormat.ISO.DATE_TIME ) LocalDateTime datetime,
            @RequestParam String targetEmail,
            RedirectAttributes redirectAttributes )
    {
        try
        {
            // 1. Busca o DTO pronto
            AttendanceDashboardDTO dto = attendanceRepository.findDashboardDataById( cpf, datetime )
                                                             .orElseThrow( () -> new IllegalArgumentException( "Atendimento não encontrado." ) );
            
            // 2. Passa o DTO e o e-mail destino diretamente para o Service
            pdfEmailService.sendEmail( dto, targetEmail );
            
            redirectAttributes.addFlashAttribute( "mensagemSucesso", "E-mail enviado com sucesso para " + targetEmail );
        }
        catch ( Exception e )
        {
            redirectAttributes.addFlashAttribute( "mensagemErro", "Erro ao enviar e-mail. Verifique se o Node.js está rodando." );
        }
        
        return "redirect:/medicalfield";
    }
}
