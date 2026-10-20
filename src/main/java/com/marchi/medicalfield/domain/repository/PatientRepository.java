package com.marchi.medicalfield.domain.repository;

import com.marchi.medicalfield.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository
        extends JpaRepository<Patient, String>
{
}