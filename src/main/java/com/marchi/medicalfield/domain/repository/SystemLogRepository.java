package com.marchi.medicalfield.domain.repository;

import com.marchi.medicalfield.domain.model.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    
    // Busca todos os logs e ordena do mais novo para o mais antigo
    List<SystemLog> findAllByOrderByTimestampDesc();
}