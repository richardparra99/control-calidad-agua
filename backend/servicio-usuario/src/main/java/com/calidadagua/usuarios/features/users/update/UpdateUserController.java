package com.calidadagua.usuarios.features.users.update;

import com.calidadagua.usuarios.shared.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UpdateUserController {
    private final UpdateUserHandler handler;

    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        UUID adminId = UUID.fromString(jwt.getSubject());

        return handler.execute(id, request, adminId);
    }
}
