package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.dto.SyncRequestDTO;
import com.marchi.medicalfield.domain.model.Attendance;
import com.marchi.medicalfield.domain.model.AttendanceId;
import com.marchi.medicalfield.domain.model.MedicalRecord;
import com.marchi.medicalfield.domain.model.Patient;
import com.marchi.medicalfield.domain.repository.AttendanceRepository;
import com.marchi.medicalfield.domain.repository.MedicalRecordRepository;
import com.marchi.medicalfield.domain.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    
    @GetMapping
    public List<Attendance> listAll() {
        return attendanceRepository.findAll();
    }
    
    @GetMapping( "/patient/{cpf}" )
    public List<Attendance> listByPatientCpf( @PathVariable String cpf )
    {
        return attendanceRepository.findByIdPatientCpf( cpf );
    }
    
    @PostMapping
    public Attendance create(@RequestBody Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
    
    // Recebe o pacote DTO completo e salva as três tabelas em uma única transação
    @PostMapping("/sync")
    @Transactional
    public Attendance syncOfflineData(@RequestBody SyncRequestDTO payload) {
        
        // 1. Extrai os dados do DTO e salva o Paciente no banco
        Patient patient = new Patient();
        patient.setCpf(payload.getPatient().getCpf());
        patient.setName(payload.getPatient().getName());
        patient.setBirthDate(payload.getPatient().getBirthDate());
        patient.setGender(payload.getPatient().getGender());
        patientRepository.save(patient);

        // 2. Extrai os dados do DTO e salva o Prontuário no banco, usando o CPF recém-salvo
        MedicalRecord record = new MedicalRecord();
        record.setPatientCpf(patient.getCpf());
        record.setOpeningDate(payload.getRecord().getOpeningDate());
        record.setObservations(payload.getRecord().getObservations());
        medicalRecordRepository.save(record);

        // 3. Monta a chave composta e salva o Atendimento
        Attendance   attendance = new Attendance();
        AttendanceId id         = new AttendanceId( patient.getCpf(), record.getOpeningDate());
        attendance.setId(id);
        
        attendance.setProfessionalId(payload.getAttendance().getProfessionalId());
        attendance.setGlasgowScale(payload.getAttendance().getGlasgowScale());
        attendance.setTriageClassification(payload.getAttendance().getTriageClassification());
        attendance.setBloodPressure(payload.getAttendance().getBloodPressure());
        attendance.setHeartRate(payload.getAttendance().getHeartRate());
        attendance.setOxygenSaturation(payload.getAttendance().getOxygenSaturation());
        attendance.setAdministeredMedications(payload.getAttendance().getAdministeredMedications());
        attendance.setSynchronizedFlag(true); // Como chegou pela rede, já está sincronizado

        return attendanceRepository.save(attendance);
    }
}