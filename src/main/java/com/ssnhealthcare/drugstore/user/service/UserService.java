package com.ssnhealthcare.drugstore.user.service;

import com.ssnhealthcare.drugstore.user.dto.request.ChangePasswordRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserCreateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserUpdateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserCreateResponseDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserGetResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserStatusResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserCreateResponseDTO createUser( UserCreateRequestDTO dto);

    void changePassword(ChangePasswordRequestDTO dto);

    UserCreateResponseDTO updateUser(Long id, UserUpdateRequestDTO dto);

    UserGetResponse getUserById(Long id);

    Page<UserGetResponse> getAllUsers(Pageable pageable);

    UserStatusResponseDTO changeStatus(Long id, boolean active);
}
