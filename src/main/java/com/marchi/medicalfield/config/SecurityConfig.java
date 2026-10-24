package com.marchi.medicalfield.config;

import com.marchi.medicalfield.domain.model.Professional;
import com.marchi.medicalfield.domain.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .csrf( AbstractHttpConfigurer::disable ) // Desativa proteção CSRF temporariamente para não bloquear o Postman
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/medicalfield/**").authenticated() // Exige login para qualquer rota que comece com /admin
                .anyRequest().permitAll() // Deixa as APIs de celular (pacientes, atendimentos) totalmente abertas
            )
                .formLogin(form -> form
                .loginPage( "/login" ) // Informa a página de login customizada
                .defaultSuccessUrl("/medicalfield", true) // Após logar, joga o usuário para o dashboard
                .permitAll() // Libera a página de login para todos
            )
                .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            );

        return http.build();
    }

    // Serviço que busca o usuário no banco de dados quando ele tenta logar
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Professional professional = professionalRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

            return User.builder()
                    .username(professional.getUsername())
                    .password(professional.getPasswordHash())
                    .roles("ADMIN") // Cargo fictício apenas para controle interno do Spring
                    .build();
        };
    }

    // O Spring Security exige que as senhas sejam criptografadas no banco (Padrão de mercado BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}