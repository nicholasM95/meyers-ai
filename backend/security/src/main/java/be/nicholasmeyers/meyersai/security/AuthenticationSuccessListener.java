package be.nicholasmeyers.meyersai.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationSuccessListener {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Jwt jwt = (Jwt) event.getAuthentication().getPrincipal();
        List<String> roles = event.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        String username = jwt.getClaimAsString("email");
        log.info("The user {} has logged in successfully with roles {}.", username, roles);
    }
}
