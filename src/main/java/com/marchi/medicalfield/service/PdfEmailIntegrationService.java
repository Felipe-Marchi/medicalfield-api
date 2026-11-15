package com.marchi.medicalfield.service;

import com.marchi.medicalfield.domain.dto.AttendanceDashboardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PdfEmailIntegrationService {

    @Value("${api.pdf-email.url}")
    private String nodeApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    
    public byte[] generatePdf( AttendanceDashboardDTO dto )
    {
        String url = nodeApiUrl + "/generate-pdf";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        
        // O RestTemplate converte o 'dto' automaticamente para um JSON completo
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                url,
                new HttpEntity<>( dto, headers ),
                byte[].class
        );
        
        return response.getBody();
    }
    
    public void sendEmail( AttendanceDashboardDTO dto, String targetEmail )
    {
        // Adiciona o e-mail na URL para deixar o corpo da requisição exclusivo para o DTO
        String url = nodeApiUrl + "/send-email?targetEmail=" + targetEmail;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        
        // Converte o DTO automaticamente para JSON
        restTemplate.postForEntity(
                url,
                new HttpEntity<>( dto, headers ),
                String.class
        );
    }
}