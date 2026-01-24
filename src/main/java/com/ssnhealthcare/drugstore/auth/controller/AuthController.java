package com.ssnhealthcare.drugstore.auth.controller;


import com.ssnhealthcare.drugstore.auth.dto.LoginRequestDTO;
import com.ssnhealthcare.drugstore.auth.dto.LoginResponseDTO;
import com.ssnhealthcare.drugstore.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }
}
