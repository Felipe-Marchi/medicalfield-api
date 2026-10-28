package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name = "professionals" )
public class Professional
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @Column( name = "username", nullable = false, unique = true, length = 50 )
    private String username;
    
    @Column( name = "name", nullable = false, length = 150 )
    private String name;
    
    @JsonIgnore
    @Column( name = "password_hash", nullable = false )
    private String passwordHash;
    
    @Column( name = "active", nullable = false )
    private Boolean active = true;
    
    public Professional() {}
    
    // Getters e Setters
    public Long getId() {return id;}
    
    public void setId( Long id ) {this.id = id;}
    
    public String getUsername() {return username;}
    
    public void setUsername( String username ) {this.username = username;}
    
    public String getName() {return name;}
    
    public void setName( String name ) {this.name = name;}
    
    public String getPasswordHash() {return passwordHash;}
    
    public void setPasswordHash( String passwordHash ) {this.passwordHash = passwordHash;}
    
    public Boolean getActive() {return active;}
    
    public void setActive( Boolean active ) {this.active = active;}
}