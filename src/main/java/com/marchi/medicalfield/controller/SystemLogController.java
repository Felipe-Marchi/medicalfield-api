package com.marchi.medicalfield.controller;

import com.marchi.medicalfield.domain.model.SystemLog;
import com.marchi.medicalfield.domain.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SystemLogController {

    @Autowired
    private SystemLogRepository systemLogRepository;

    @GetMapping("/medicalfield/logs")
    public String viewLogs(Model model) {
        List<SystemLog> logs = systemLogRepository.findAllByOrderByTimestampDesc();
        model.addAttribute("logs", logs);
        return "logs"; // Renderiza o arquivo logs.html
    }
}