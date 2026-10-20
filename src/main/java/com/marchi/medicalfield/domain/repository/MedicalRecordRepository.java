package com.marchi.medicalfield.domain.repository;

import com.marchi.medicalfield.domain.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, String>
{
}