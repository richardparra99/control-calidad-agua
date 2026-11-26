package com.calidadagua.usuarios.features.users.list;

import com.calidadagua.usuarios.shared.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ListUsersController {
    private final ListUsersHandler handler;

    @GetMapping
    public List<UserResponse> list() {

        return handler.execute();
    }
}
