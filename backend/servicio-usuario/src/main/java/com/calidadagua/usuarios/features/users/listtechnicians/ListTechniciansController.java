package com.calidadagua.usuarios.features.users.listtechnicians;

import com.calidadagua.usuarios.shared.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ListTechniciansController {
    private final ListTechniciansHandler handler;

    @GetMapping("/technicians")
    public List<UserResponse> listTechnicians() {

        return handler.execute();
    }
}
