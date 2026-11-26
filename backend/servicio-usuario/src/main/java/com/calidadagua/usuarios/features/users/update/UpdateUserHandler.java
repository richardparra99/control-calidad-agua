package com.calidadagua.usuarios.features.users.update;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
import com.calidadagua.usuarios.shared.exception.DuplicateEmailException;
import com.calidadagua.usuarios.shared.exception.OperationNotAllowedException;
import com.calidadagua.usuarios.shared.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserHandler {
    private final UserRepository repository;

    @Transactional
    public UserResponse execute(
            UUID id,
            UpdateUserRequest request,
            UUID adminId) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (!user.getEmail().equals(email)
                && repository.existsByEmail(email)) {

            throw new DuplicateEmailException(
                    "Email is already registered"
            );
        }

        if (id.equals(adminId)
                && request.role() != user.getRole()) {

            throw new OperationNotAllowedException(
                    "You cannot change your own role"
            );
        }

        if (user.isActive()
                && user.getRole() == Role.ADMIN
                && request.role() != Role.ADMIN
                && repository.countByRoleAndActiveTrue(
                Role.ADMIN) <= 1) {

            throw new OperationNotAllowedException(
                    "At least one active admin is required"
            );
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(email);
        user.setPhone(request.phone());
        user.setRole(request.role());

        return UserResponse.from(
                repository.saveAndFlush(user)
        );
    }
}
