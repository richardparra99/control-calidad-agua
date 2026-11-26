package com.calidadagua.usuarios.features.users.create;

import com.calidadagua.usuarios.domain.model.User;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
import com.calidadagua.usuarios.shared.exception.DuplicateEmailException;
import com.calidadagua.usuarios.shared.exception.OperationNotAllowedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CreateUserHandler {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse execute(CreateUserRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (repository.existsByEmail(email)) {
            throw new DuplicateEmailException(
                    "Email is already registered"
            );
        }

        if (request.password()
                .getBytes(StandardCharsets.UTF_8).length > 72) {

            throw new OperationNotAllowedException(
                    "Password exceeds the BCrypt byte limit"
            );
        }

        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(email);

        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        user.setPhone(request.phone());
        user.setRole(request.role());
        user.setActive(true);

        User savedUser = repository.saveAndFlush(user);

        return UserResponse.from(savedUser);
    }
}
