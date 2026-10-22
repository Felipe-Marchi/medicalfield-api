package com.marchi.medicalfield.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Esta classe não tem a anotação @Entity, pois ela não vai para o banco de dados.
// Ela serve apenas para receber e organizar o JSON gigante que virá do celular.
public class SyncRequestDTO
{
    
    private PatientDTO    patient;
    private RecordDTO     record;
    private AttendanceDTO attendance;
    
    public PatientDTO getPatient() {return patient;}
    
    public void setPatient( PatientDTO patient ) {this.patient = patient;}
    
    public RecordDTO getRecord() {return record;}
    
    public void setRecord( RecordDTO record ) {this.record = record;}
    
    public AttendanceDTO getAttendance() {return attendance;}
    
    public void setAttendance( AttendanceDTO attendance ) {this.attendance = attendance;}
    
    // --- Classes Internas para mapear a estrutura do JSON ---
    
    public static class PatientDTO
    {
        private String    cpf;
        private String    name;
        private LocalDate birthDate;
        private String    gender;
        
        public String getCpf() {return cpf;}
        
        public void setCpf( String cpf ) {this.cpf = cpf;}
        
        public String getName() {return name;}
        
        public void setName( String name ) {this.name = name;}
        
        public LocalDate getBirthDate() {return birthDate;}
        
        public void setBirthDate( LocalDate birthDate ) {this.birthDate = birthDate;}
        
        public String getGender() {return gender;}
        
        public void setGender( String gender ) {this.gender = gender;}
    }
    
    public static class RecordDTO
    {
        private LocalDateTime openingDate;
        private String        observations;
        
        public LocalDateTime getOpeningDate() {return openingDate;}
        
        public void setOpeningDate( LocalDateTime openingDate ) {this.openingDate = openingDate;}
        
        public String getObservations() {return observations;}
        
        public void setObservations( String observations ) {this.observations = observations;}
    }
    
    public static class AttendanceDTO
    {
        private Long    professionalId;
        private Integer glasgowScale;
        private String  triageClassification;
        private String  bloodPressure;
        private Integer heartRate;
        private Integer oxygenSaturation;
        private String  administeredMedications;
        
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
    }
}