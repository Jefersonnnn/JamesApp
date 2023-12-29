package com.jm.jamesapp.configs.persistence;

import com.jm.jamesapp.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private IAuthenticationFacade authenticationFacade;

    public AuditorAwareImpl(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        var auth = authenticationFacade.getAuthentication();
        if (auth == null) return Optional.empty();
        return Optional.ofNullable(auth.getName());
    }
}
