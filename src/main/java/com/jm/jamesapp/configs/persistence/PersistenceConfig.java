package com.jm.jamesapp.configs.persistence;

import com.jm.jamesapp.security.IAuthenticationFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    private IAuthenticationFacade authenticationFacade;

    public PersistenceConfig(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl(this.authenticationFacade);
    }
}
