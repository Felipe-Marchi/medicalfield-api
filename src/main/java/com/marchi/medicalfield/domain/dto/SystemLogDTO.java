package com.marchi.medicalfield.domain.dto;

public class SystemLogDTO {
    private Long professionalId;
    private String clientIp;
    private String httpMethod;
    private String route;
    private Integer statusCode;
    private Long processingTimeMs;
    private String message;

    // Construtor
    public SystemLogDTO(Long professionalId, String clientIp, String httpMethod, String route, Integer statusCode, Long processingTimeMs, String message) {
        this.professionalId = professionalId;
        this.clientIp = clientIp;
        this.httpMethod = httpMethod;
        this.route = route;
        this.statusCode = statusCode;
        this.processingTimeMs = processingTimeMs;
        this.message = message;
    }

    // Getters
    public Long getProfessionalId() { return professionalId; }
    public String getClientIp() { return clientIp; }
    public String getHttpMethod() { return httpMethod; }
    public String getRoute() { return route; }
    public Integer getStatusCode() { return statusCode; }
    public Long getProcessingTimeMs() { return processingTimeMs; }
    public String getMessage() { return message; }
}