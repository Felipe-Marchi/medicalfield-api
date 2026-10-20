package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// Mapeia a entidade para a tabela attendances
@Entity
@Table( name = "attendances" )
public class Attendance
{
    
    // Incorpora a chave composta criada na classe AttendanceId
    @EmbeddedId
    private AttendanceId id;
    
    // Registra qual profissional realizou o socorro
    @Column( name = "professional_id", nullable = false )
    private Long professionalId;
    
    @Column( name = "glasgow_scale", nullable = false )
    private Integer glasgowScale;
    
    @Column( name = "triage_classification", nullable = false, length = 20 )
    private String triageClassification;
    
    @Column( name = "blood_pressure", length = 10 )
    private String bloodPressure;
    
    @Column( name = "heart_rate" )
    private Integer heartRate;
    
    @Column( name = "oxygen_saturation" )
    private Integer oxygenSaturation;
    
    // O JSON de medicamentos é mapeado como String nativa para simplificar o parse no backend
    @Column( name = "administered_medications", columnDefinition = "json" )
    private String administeredMedications;
    
    // Flag de controle offline-first
    @Column( name = "synchronized", nullable = false )
    private Boolean synchronizedFlag = false;
    
    public Attendance() {}
    
    // Getters e Setters
    public AttendanceId getId() {return id;}
    
    public void setId( AttendanceId id ) {this.id = id;}
    
    public Long getProfessionalId() {return professionalId;}
    
    public void setProfessionalId( Long professionalId ) {this.professionalId = professionalId;}
    
    public Integer getGlasgowScale() {return glasgowScale;}
    
    public void setGlasgowScale( Integer glasgowScale ) {this.glasgowScale = glasgowScale;}
    
    public String getTriageClassification() {return triageClassification;}
    
    public void setTriageClassification( String triageClassification ) {this.triageClassification = triageClassification;}
    
    public String getBloodPressure() {return bloodPressure;}
    
    public void setBloodPressure( String bloodPressure ) {this.bloodPressure = bloodPressure;}
    
    public Integer getHeartRate() {return heartRate;}
    
    public void setHeartRate( Integer heartRate ) {this.heartRate = heartRate;}
    
    public Integer getOxygenSaturation() {return oxygenSaturation;}
    
    public void setOxygenSaturation( Integer oxygenSaturation ) {this.oxygenSaturation = oxygenSaturation;}
    
    public String getAdministeredMedications() {return administeredMedications;}
    
    public void setAdministeredMedications( String administeredMedications ) {this.administeredMedications = administeredMedications;}
    
    public Boolean getSynchronizedFlag() {return synchronizedFlag;}
    
    public void setSynchronizedFlag( Boolean synchronizedFlag ) {this.synchronizedFlag = synchronizedFlag;}
}