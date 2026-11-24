package com.marchi.medicalfield.config;

import com.marchi.medicalfield.domain.model.Professional;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ProfessionalRepository professionalRepository;

    // Regras de bloqueio e liberação de portas
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Libera EXCLUSIVAMENTE a página e os endpoints de Login + recursos estáticos
                .requestMatchers(
                        "/login",
                        "/professionals/login",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/assets/**",
                        "/*.png",
                        "/*.ico",
                        "/error",
                        "/v3/api-docs/**",     // Libera o JSON da documentação
                        "/swagger-ui/**",      // Libera a interface visual do Swagger
                        "/swagger-ui.html"
                )
                .permitAll()
                // EXIGE AUTENTICAÇÃO OBRIGATÓRIA PARA TODAS AS OUTRAS ROTAS DO SISTEMA
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/medicalfield", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            // Permite autenticação HTTP Basic para chamadas de API (Postman / App móvel)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Professional professional = professionalRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Profissional não encontrado com credencial: " + username));

            return User.builder()
                    .username(professional.getUsername())
                    .password(professional.getPasswordHash())
                    .roles("PROFESSIONAL")
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}