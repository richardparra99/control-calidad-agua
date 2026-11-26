package com.calidadagua.usuarios.features.users.getbyid;

import com.calidadagua.usuarios.shared.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class GetUserController {
    private final GetUserHandler handler;

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable UUID id) {

        return handler.execute(id);
    }
}
