package com.calidadagua.usuarios.features.auth.login;

import com.calidadagua.usuarios.shared.dto.UserResponse;

public record LoginResponse(
        String token,
        String type,
        UserResponse user
) {
}
