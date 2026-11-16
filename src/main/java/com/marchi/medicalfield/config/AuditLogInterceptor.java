package com.marchi.medicalfield.config;

import com.marchi.medicalfield.domain.dto.SystemLogDTO;
import com.marchi.medicalfield.domain.model.Professional;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import com.marchi.medicalfield.service.LogIntegrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AuditLogInterceptor implements HandlerInterceptor {

    @Autowired
    private LogIntegrationService logService;

    @Autowired
    private ProfessionalRepository professionalRepository;

    // Disparado no exato momento que a requisição bate no servidor
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Marca o tempo de início e guarda dentro da requisição
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    // Disparado no exato momento em que o servidor devolve a resposta para o Flutter/Navegador
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long processingTimeMs = System.currentTimeMillis() - startTime;

        String route = request.getRequestURI();
        
        // Ignora arquivos estáticos (CSS, JS, imagens) para não poluir o banco de logs
        if (route.startsWith("/css/") || route.startsWith("/js/") || route.contains(".")) {
            return;
        }

        // Descobre o IP real do cliente
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }

        // Tenta capturar o usuário logado via Spring Security
        Long professionalId = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            Optional<Professional> prof = professionalRepository.findByUsername(auth.getName());
            if (prof.isPresent()) {
                professionalId = prof.get().getId();
            }
        }

        // Monta o DTO
        SystemLogDTO logDTO = new SystemLogDTO(
                professionalId,
                clientIp,
                request.getMethod(),
                route,
                response.getStatus(),
                processingTimeMs,
                ex != null ? "Erro: " + ex.getMessage() : "Acesso finalizado com sucesso."
        );

        // Dispara assincronamente para o Python
        logService.sendAuditLog(logDTO);
    }
}