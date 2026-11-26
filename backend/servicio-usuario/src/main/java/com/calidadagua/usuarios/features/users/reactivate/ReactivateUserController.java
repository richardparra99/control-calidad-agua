package com.calidadagua.usuarios.features.users.reactivate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ReactivateUserController {
    private final ReactivateUserHandler handler;

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(
            @PathVariable UUID id) {

        handler.execute(id);

        return ResponseEntity.noContent().build();
    }
}
