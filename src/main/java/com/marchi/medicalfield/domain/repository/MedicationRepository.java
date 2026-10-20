package com.marchi.medicalfield.domain.repository;

import com.marchi.medicalfield.domain.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository
        extends JpaRepository<Medication, Long>
{
}