package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table( name = "medical_records" )
public class MedicalRecord
{
    
    @Id
    @Column( name = "patient_cpf", length = 11, nullable = false )
    private String patientCpf;
    
    @Column( name = "opening_date", nullable = false )
    private LocalDateTime openingDate;
    
    @Column( name = "observations", columnDefinition = "TEXT" )
    private String observations;
    
    public MedicalRecord() {}
    
    // Getters e Setters
    public String getPatientCpf() {return patientCpf;}
    
    public void setPatientCpf( String patientCpf ) {this.patientCpf = patientCpf;}
    
    public LocalDateTime getOpeningDate() {return openingDate;}
    
    public void setOpeningDate( LocalDateTime openingDate ) {this.openingDate = openingDate;}
    
    public String getObservations() {return observations;}
    
    public void setObservations( String observations ) {this.observations = observations;}
}