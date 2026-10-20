package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// Mapeia a entidade para a tabela medical_records
@Entity
@Table( name = "medical_records" )
public class MedicalRecord
{
    
    // Utiliza o CPF do paciente como identificador e chave primária
    @Id
    @Column( name = "patient_cpf", length = 11, nullable = false )
    private String patientCpf;
    
    // Define a data e hora em que a ficha clínica foi iniciada
    @Column( name = "opening_date", nullable = false )
    private LocalDateTime openingDate;
    
    // Armazena histórico clínico, comorbidades e alergias
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