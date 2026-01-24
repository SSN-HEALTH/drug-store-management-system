package com.ssnhealthcare.drugstore.auth.service;

import com.ssnhealthcare.drugstore.auth.dto.LoginRequestDTO;
import com.ssnhealthcare.drugstore.auth.dto.LoginResponseDTO;
import jakarta.validation.Valid;


public interface AuthService {
     LoginResponseDTO login(@Valid LoginRequestDTO dto);
}
