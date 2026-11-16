package com.marchi.medicalfield.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync // Habilita o processamento em segundo plano que usamos no Service
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInterceptor auditLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registra o nosso interceptor para "escutar" todas as rotas (/**)
        registry.addInterceptor(auditLogInterceptor).addPathPatterns("/**");
    }
}