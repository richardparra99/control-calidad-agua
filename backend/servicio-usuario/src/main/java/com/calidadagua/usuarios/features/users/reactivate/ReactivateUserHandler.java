package com.calidadagua.usuarios.features.users.reactivate;

import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactivateUserHandler {
    private final UserRepository repository;

    @Transactional
    public void execute(UUID id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );
        user.reactivate();
        repository.save(user);
    }
}
