package com.calidadagua.usuarios.features.users.deactivate;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.exception.OperationNotAllowedException;
import com.calidadagua.usuarios.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeactivateUserHandler {
    private final UserRepository repository;

    @Transactional
    public void execute(UUID id, UUID adminId) {

        if (id.equals(adminId)) {

            throw new OperationNotAllowedException(
                    "You cannot deactivate your own account"
            );
        }

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );

        if (user.isActive()
                && user.getRole() == Role.ADMIN
                && repository.countByRoleAndActiveTrue(
                Role.ADMIN) <= 1) {

            throw new OperationNotAllowedException(
                    "Cannot deactivate the last active admin"
            );
        }

        user.desactive();
        repository.save(user);
    }
}
