package org.hoyo.celestia.security.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthStatusController {

    @GetMapping("/api/auth/status")
    public Map<String, Object> authStatus(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();

        boolean isRealUser = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)
                && principal != null;

        if (isRealUser) {
            response.put("authenticated", true);
            response.put("username", principal.getAttribute("username"));
            response.put("user", principal.getAttributes());
        } else {
            response.put("authenticated", false);
        }
        return response;
    }

    @GetMapping("/csrf-token")
    public CsrfToken csrf(CsrfToken token) {
        // Spring injects the current CSRF token and also sets cookie in response headers
        return token;
    }

}
