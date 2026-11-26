package com.calidadagua.usuarios.features.users.list;

import com.calidadagua.usuarios.infrastructure.persistence.UserRepository;
import com.calidadagua.usuarios.shared.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUsersHandler {
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public List<UserResponse> execute() {

        return repository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}
