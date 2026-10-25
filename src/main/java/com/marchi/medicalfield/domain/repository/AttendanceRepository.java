package com.marchi.medicalfield.domain.repository;

import java.util.List;

import com.marchi.medicalfield.domain.dto.AttendanceDashboardDTO;
import com.marchi.medicalfield.domain.model.Attendance;
import com.marchi.medicalfield.domain.model.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, AttendanceId>
{
    // Busca os dados de Atendimento, Paciente e Prontuário em uma única consulta
    @Query("SELECT new com.marchi.medicalfield.domain.dto.AttendanceDashboardDTO(" +
           "a.id.patientCpf, p.name, a.id.attendanceDatetime, a.triageClassification, " +
           "a.glasgowScale, a.bloodPressure, a.heartRate, a.oxygenSaturation, " +
           "a.administeredMedications, m.observations) " +
           "FROM Attendance a " +
           "JOIN Patient p ON a.id.patientCpf = p.cpf " +
           "LEFT JOIN MedicalRecord m ON a.id.patientCpf = m.patientCpf " +
           "ORDER BY a.id.attendanceDatetime DESC")
    List<AttendanceDashboardDTO> findAllDashboardData();
}