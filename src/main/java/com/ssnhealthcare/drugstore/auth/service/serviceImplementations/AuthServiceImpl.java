package com.ssnhealthcare.drugstore.auth.service.serviceImplementations;

import com.ssnhealthcare.drugstore.auth.service.AuthService;
import org.springframework.stereotype.Service;

import com.ssnhealthcare.drugstore.auth.dto.LoginRequestDTO;
import com.ssnhealthcare.drugstore.auth.dto.LoginResponseDTO;
import com.ssnhealthcare.drugstore.security.JwtUtil;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    dto.getUsername(),
                                    dto.getPassword()
                            )
                    );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
}
