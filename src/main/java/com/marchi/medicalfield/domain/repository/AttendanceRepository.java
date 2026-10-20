package com.marchi.medicalfield.domain.repository;

import com.marchi.medicalfield.domain.model.Attendance;
import com.marchi.medicalfield.domain.model.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, AttendanceId>
{
}