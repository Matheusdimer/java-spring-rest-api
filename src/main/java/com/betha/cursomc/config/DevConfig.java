package com.betha.cursomc.config;

import com.betha.cursomc.services.EmailService;
import com.betha.cursomc.services.SMTPEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {
    @Bean
    public EmailService emailService() {
        return new SMTPEmailService();
    }
}
