package com.maxaix.serviceimpl;

import com.maxaix.config.JwtTokenUtil;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String currentUsername = jwtTokenUtil.getCurrentUsername();
        if (currentUsername == null) {
            return Optional.empty();
        }
        return Optional.of(currentUsername);
    }
}
