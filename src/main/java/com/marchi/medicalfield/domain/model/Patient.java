package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table( name = "patients" )
public class Patient
{
    
    @Id
    @Column( name = "cpf", length = 11, nullable = false )
    private String cpf;
    
    @Column( name = "name", nullable = false, length = 150 )
    private String name;
    
    @Column( name = "birth_date" )
    private LocalDate birthDate;
    
    @Column( name = "gender", length = 1 )
    private String gender;
    
    public Patient() {}
    
    // Getters e Setters
    public String getCpf() {return cpf;}
    
    public void setCpf( String cpf ) {this.cpf = cpf;}
    
    public String getName() {return name;}
    
    public void setName( String name ) {this.name = name;}
    
    public LocalDate getBirthDate() {return birthDate;}
    
    public void setBirthDate( LocalDate birthDate ) {this.birthDate = birthDate;}
    
    public String getGender() {return gender;}
    
    public void setGender( String gender ) {this.gender = gender;}
}