package com.calidadagua.usuarios.shared.dto;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(UUID id,
                           String firstName,
                           String lastName,
                           String email,
                           String phone,
                           Role role,
                           boolean active,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
    public static UserResponse from(User usuario) {
        return new UserResponse(
                usuario.getId(),
                usuario.getFirstName(),
                usuario.getLastName(),
                usuario.getEmail(),
                usuario.getPhone(),
                usuario.getRole(),
                usuario.isActive(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}
