package com.marchi.medicalfield.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_logs")
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    
    @Column(name = "professional_id")
    private Long professionalId;
    
    @Column(name = "client_ip")
    private String clientIp;
    
    @Column(name = "http_method")
    private String httpMethod;
    
    private String route;
    
    @Column(name = "status_code")
    private Integer statusCode;
    
    @Column(name = "processing_time_ms")
    private Long processingTimeMs;
    
    private String message;

    // Getters para o Thymeleaf ler os dados
    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Long getProfessionalId() { return professionalId; }
    public String getClientIp() { return clientIp; }
    public String getHttpMethod() { return httpMethod; }
    public String getRoute() { return route; }
    public Integer getStatusCode() { return statusCode; }
    public Long getProcessingTimeMs() { return processingTimeMs; }
    public String getMessage() { return message; }
}