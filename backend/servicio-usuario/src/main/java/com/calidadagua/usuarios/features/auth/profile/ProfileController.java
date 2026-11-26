package com.calidadagua.usuarios.features.auth.profile;

import com.calidadagua.usuarios.shared.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileHandler handler;

    @GetMapping("/profile")
    public UserResponse getProfile(
            @AuthenticationPrincipal Jwt jwt) {

        UUID id = UUID.fromString(jwt.getSubject());

        return handler.execute(id);
    }
}
