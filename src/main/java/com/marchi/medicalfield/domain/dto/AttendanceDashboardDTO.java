package com.marchi.medicalfield.domain.dto;

import java.time.LocalDateTime;

public class AttendanceDashboardDTO
{
    
    private String        patientCpf;
    private String        patientName;
    private LocalDateTime attendanceDatetime;
    private String        triageClassification;
    private Integer       glasgowScale;
    private String        bloodPressure;
    private Integer       heartRate;
    private Integer       oxygenSaturation;
    private String        administeredMedications;
    private String        medicalRecordObservations;
    
    public AttendanceDashboardDTO( String patientCpf, String patientName, LocalDateTime attendanceDatetime,
                                   String triageClassification, Integer glasgowScale, String bloodPressure,
                                   Integer heartRate, Integer oxygenSaturation, String administeredMedications,
                                   String medicalRecordObservations )
    {
        this.patientCpf = patientCpf;
        this.patientName = patientName;
        this.attendanceDatetime = attendanceDatetime;
        this.triageClassification = triageClassification;
        this.glasgowScale = glasgowScale;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
        this.administeredMedications = administeredMedications;
        this.medicalRecordObservations = medicalRecordObservations;
    }
    
    // Getters
    public String getPatientCpf() {return patientCpf;}
    
    public String getPatientName() {return patientName;}
    
    public LocalDateTime getAttendanceDatetime() {return attendanceDatetime;}
    
    public String getTriageClassification() {return triageClassification;}
    
    public Integer getGlasgowScale() {return glasgowScale;}
    
    public String getBloodPressure() {return bloodPressure;}
    
    public Integer getHeartRate() {return heartRate;}
    
    public Integer getOxygenSaturation() {return oxygenSaturation;}
    
    public String getAdministeredMedications() {return administeredMedications;}
    
    public String getMedicalRecordObservations() {return medicalRecordObservations;}
}