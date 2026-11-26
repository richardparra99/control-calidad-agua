
package com.calidadagua.usuarios.infrastructure.config;

import com.calidadagua.usuarios.domain.model.Role;
import com.calidadagua.usuarios.features.users.create.CreateUserHandler;
import com.calidadagua.usuarios.features.users.create.CreateUserRequest;
import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final CreateUserHandler createUserHandler;
    private final Validator validator;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        if (email.isBlank() || password.isBlank()) {

            throw new IllegalStateException(
                    "Initial admin credentials are required"
            );
        }

        CreateUserRequest request =
                new CreateUserRequest(
                        "System",
                        "Administrator",
                        email.trim().toLowerCase(Locale.ROOT),
                        password,
                        null,
                        Role.ADMIN
                );

        if (!validator.validate(request).isEmpty()) {

            throw new IllegalStateException(
                    "Invalid initial admin credentials"
            );
        }

        createUserHandler.execute(request);
    }
}
