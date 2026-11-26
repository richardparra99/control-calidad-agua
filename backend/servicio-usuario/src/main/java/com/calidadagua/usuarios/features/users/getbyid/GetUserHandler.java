package com.calidadagua.usuarios.features.users.getbyid;

import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
import com.calidadagua.usuarios.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetUserHandler {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public UserResponse execute(UUID id) {

        return repository.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );
    }
}
