package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

// Classe obrigatória no JPA para representar chaves primárias compostas
@Embeddable
public class AttendanceId
        implements Serializable
{
    
    @Column( name = "patient_cpf", length = 11, nullable = false )
    private String patientCpf;
    
    @Column( name = "attendance_datetime", nullable = false )
    private LocalDateTime attendanceDatetime;
    
    public AttendanceId() {}
    
    public AttendanceId( String patientCpf, LocalDateTime attendanceDatetime )
    {
        this.patientCpf = patientCpf;
        this.attendanceDatetime = attendanceDatetime;
    }
    
    // Getters e Setters
    public String getPatientCpf() {return patientCpf;}
    
    public void setPatientCpf( String patientCpf ) {this.patientCpf = patientCpf;}
    
    public LocalDateTime getAttendanceDatetime() {return attendanceDatetime;}
    
    public void setAttendanceDatetime( LocalDateTime attendanceDatetime ) {this.attendanceDatetime = attendanceDatetime;}
    
    // Os métodos equals e hashCode são exigidos pela interface Serializable para comparar chaves
    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {return true;}
        if ( o == null || getClass() != o.getClass() )
        {return false;}
        AttendanceId that = (AttendanceId) o;
        return Objects.equals( patientCpf, that.patientCpf ) &&
                Objects.equals( attendanceDatetime, that.attendanceDatetime );
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash( patientCpf, attendanceDatetime );
    }
}