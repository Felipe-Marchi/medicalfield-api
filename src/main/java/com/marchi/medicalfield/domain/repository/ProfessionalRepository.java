package com.marchi.medicalfield.domain.repository;

import java.util.Optional;

import com.marchi.medicalfield.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository
        extends JpaRepository<Professional, Long>
{
    Optional<Professional> findByUsername( String username);
}