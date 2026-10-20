package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Mapeia a entidade para a tabela professionals
@Entity
@Table( name = "professionals" )
public class Professional
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    // Garante a unicidade do registro (ex: CRM, COREN)
    @Column( name = "council_registration", nullable = false, unique = true, length = 50 )
    private String councilRegistration;
    
    @Column( name = "name", nullable = false, length = 150 )
    private String name;
    
    @Column( name = "password_hash", nullable = false )
    private String passwordHash;
    
    @Column( name = "active", nullable = false )
    private Boolean active = true;
    
    // Construtor padrão
    public Professional() {}
    
    // Getters e Setters
    public Long getId() {return id;}
    
    public void setId( Long id ) {this.id = id;}
    
    public String getCouncilRegistration() {return councilRegistration;}
    
    public void setCouncilRegistration( String councilRegistration ) {this.councilRegistration = councilRegistration;}
    
    public String getName() {return name;}
    
    public void setName( String name ) {this.name = name;}
    
    public String getPasswordHash() {return passwordHash;}
    
    public void setPasswordHash( String passwordHash ) {this.passwordHash = passwordHash;}
    
    public Boolean getActive() {return active;}
    
    public void setActive( Boolean active ) {this.active = active;}
}