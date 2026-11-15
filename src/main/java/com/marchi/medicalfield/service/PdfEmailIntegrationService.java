package com.marchi.medicalfield.service;

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

    public byte[] generatePdf(String patientName, String medicalDetails) {
        String url = nodeApiUrl + "/generate-pdf";
        
        Map<String, String> body = new HashMap<>();
        body.put("patientName", patientName);
        body.put("medicalDetails", medicalDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Passando a entidade direto no método, sem criar variável
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
                url, 
                new HttpEntity<>(body, headers), 
                byte[].class
        );
        
        return response.getBody();
    }

    public void sendEmail(String patientName, String medicalDetails, String targetEmail) {
        String url = nodeApiUrl + "/send-email";

        Map<String, String> body = new HashMap<>();
        body.put("patientName", patientName);
        body.put("medicalDetails", medicalDetails);
        body.put("targetEmail", targetEmail);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Passando a entidade direto no método, sem criar variável
        restTemplate.postForEntity(
                url, 
                new HttpEntity<>(body, headers), 
                String.class
        );
    }
}