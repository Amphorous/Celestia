package org.hoyo.celestia.security.controller;

import org.hoyo.celestia.security.configuration.AESUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
            String platform = null;
            String avatarUrl = null;

            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                platform = oauthToken.getAuthorizedClientRegistrationId(); // e.g., "discord"
            }

            if ("discord".equals(platform)) {
                String userId = principal.getAttribute("id");
                String avatarHash = principal.getAttribute("avatar");

                if (userId != null && avatarHash != null) {
                    // Custom avatar
                    avatarUrl = String.format(
                            "https://cdn.discordapp.com/avatars/%s/%s.png",
                            userId, avatarHash
                    );
                } else if (userId != null) {
                    // Default avatar
                    String discriminator = principal.getAttribute("discriminator");
                    int discIndex = 0;
                    try {
                        discIndex = Integer.parseInt(discriminator) % 5;
                    } catch (Exception ignored) {}
                    avatarUrl = String.format(
                            "https://cdn.discordapp.com/embed/avatars/%d.png",
                            discIndex
                    );
                }

                String key = principal.getAttribute("username");
                try {
                    String encrypted_key = AESUtil.encrypt(key);
                    response.put("ENCRYPTED_KEY", encrypted_key);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            response.put("authenticated", true);
            response.put("username", principal.getAttribute("global_name"));
            response.put("platform", platform);
            response.put("avatarUrl", avatarUrl);

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
