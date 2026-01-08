package com.ssnhealthcare.drugstore.user.service.implementation;

import com.ssnhealthcare.drugstore.exception.InvalidPasswordException;
import com.ssnhealthcare.drugstore.exception.ResourceAlreadyExistException;
import com.ssnhealthcare.drugstore.exception.UserNotFoundException;
import com.ssnhealthcare.drugstore.exception.UserStatusAlreadySetException;
import com.ssnhealthcare.drugstore.user.dto.request.ChangePasswordRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserUpdateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.ChangePasswordResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserResponseDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserStatusResponseDTO;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import com.ssnhealthcare.drugstore.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceAlreadyExistException(
                    "Username '" + dto.getUsername() + "' already exists"
            );
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(true);

        return mapToUserResponse(userRepository.save(user));
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequestDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (dto.getNewPassword() == null || dto.getNewPassword().isBlank()) {
            throw new InvalidPasswordException("Password cannot be empty");
        }

        user.setPassword(dto.getNewPassword());

        ChangePasswordResponse response = new ChangePasswordResponse();
        response.setUserId(user.getUserId());
        response.setMessage("Password changed successfully");

        return response;
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id));

        user.setUsername(dto.getUsername());
        user.setActive(dto.getActive());
        user.setRole(dto.getRole());

        return mapToUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id));

        return mapToUserResponse(user);
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::mapToUserResponse);
    }

    @Transactional
    @Override
    public UserStatusResponseDTO changeStatus(Long id, boolean active) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id));

        if (user.isActive() == active) {
            throw new UserStatusAlreadySetException(
                    "User status already " + (active ? "active" : "inactive"));
        }

        user.setActive(active);

        UserStatusResponseDTO response = new UserStatusResponseDTO();
        response.setUserId(user.getUserId());
        response.setActive(user.isActive());
        response.setMessage("User status updated successfully");

        return response;
    }

    // ================= MAPPER =================
    private UserResponseDTO mapToUserResponse(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
}


