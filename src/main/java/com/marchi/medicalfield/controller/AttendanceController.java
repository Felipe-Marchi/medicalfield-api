package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.Attendance;
import com.marchi.medicalfield.domain.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @GetMapping
    public List<Attendance> listAll() {
        return attendanceRepository.findAll();
    }
    
    @PostMapping
    public Attendance create(@RequestBody Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
}