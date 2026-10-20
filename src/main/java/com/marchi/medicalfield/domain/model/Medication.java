package com.marchi.medicalfield.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Mapeia a entidade para a tabela medications (Catálogo)
@Entity
@Table( name = "medications" )
public class Medication
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @Column( name = "name", nullable = false, length = 100 )
    private String name;
    
    @Column( name = "concentration", length = 50 )
    private String concentration;
    
    public Medication() {}
    
    // Getters e Setters
    public Long getId() {return id;}
    
    public void setId( Long id ) {this.id = id;}
    
    public String getName() {return name;}
    
    public void setName( String name ) {this.name = name;}
    
    public String getConcentration() {return concentration;}
    
    public void setConcentration( String concentration ) {this.concentration = concentration;}
}