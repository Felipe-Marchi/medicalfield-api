package com.marchi.medicalfield.service;

import com.marchi.medicalfield.domain.dto.SystemLogDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LogIntegrationService {

    @Value("${api.python-logger.url}")
    private String pythonLoggerUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public void sendAuditLog(SystemLogDTO logDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<SystemLogDTO> request = new HttpEntity<>(logDTO, headers);
            
            // Faz o POST para o microsserviço em Python
            restTemplate.postForEntity(pythonLoggerUrl, request, String.class);
        } catch (Exception e) {
            // Em sistemas de log, se o log falhar, apenas registramos no console 
            // para não travar o fluxo principal do usuário
            System.err.println("Falha ao enviar log para o Python: " + e.getMessage());
        }
    }
}