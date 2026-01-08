package com.ssnhealthcare.drugstore.user.service;

import com.ssnhealthcare.drugstore.user.dto.request.ChangePasswordRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserUpdateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.ChangePasswordResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserResponseDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserGetResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserStatusResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);

    ChangePasswordResponse changePassword(ChangePasswordRequestDTO dto);

    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto);

    UserResponseDTO getUserById(Long id);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserStatusResponseDTO changeStatus(Long id, boolean active);
}
