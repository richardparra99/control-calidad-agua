package com.calidadagua.usuarios.features.users.deactivate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class DeactivateUserController {
    private final DeactivateUserHandler handler;

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivate(
            @PathVariable UUID id,
            @AuthenticationPrincipal Jwt jwt) {

        UUID adminId = UUID.fromString(jwt.getSubject());

        handler.execute(id, adminId);

        return ResponseEntity.ok(
                Map.of(
                        "message", "User deactivated successfully",
                        "id", id,
                        "active", false
                )
        );
    }
}
