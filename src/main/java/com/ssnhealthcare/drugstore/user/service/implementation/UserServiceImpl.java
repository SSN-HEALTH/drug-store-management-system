package com.ssnhealthcare.drugstore.user.service.implementation;

import com.ssnhealthcare.drugstore.exception.InvalidPasswordException;
import com.ssnhealthcare.drugstore.exception.ResourceAlreadyExistException;
import com.ssnhealthcare.drugstore.exception.UserNotFoundException;
import com.ssnhealthcare.drugstore.exception.UserStatusAlreadySetException;
import com.ssnhealthcare.drugstore.user.dto.request.ChangePasswordRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserCreateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserUpdateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserCreateResponseDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserGetResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserStatusResponseDTO;
import com.ssnhealthcare.drugstore.user.entity.User;
import com.ssnhealthcare.drugstore.user.repository.UserRepository;
import com.ssnhealthcare.drugstore.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserCreateResponseDTO createUser(UserCreateRequestDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new ResourceAlreadyExistException(
                    "Username '" + dto.getUsername() + "' already exists"
            );
        }
        User user =new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setActive(true);
        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }
    public UserCreateResponseDTO  mapToResponseDTO(User user){
        UserCreateResponseDTO dto=new UserCreateResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }

    public void changePassword(ChangePasswordRequestDTO dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (dto.getNewPassword() == null || dto.getNewPassword().isBlank()) {
            throw new InvalidPasswordException("Password cannot be empty");
        }

        user.setPassword(dto.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public UserCreateResponseDTO updateUser(Long id, UserUpdateRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + id));

        user.setUsername(dto.getUsername());
        user.setActive(dto.getActive());
        user.setRole(dto.getRole());

        User updatedUser = userRepository.save(user);

        return mapToResponseDTO(updatedUser);
    }
    public UserGetResponse  mapToResponseGetDTO(User user){
        UserGetResponse dto=new UserGetResponse();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
    @Override
    public UserGetResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("the user is not found with id: "+id));
        return mapToResponseGetDTO(user);

    }

    @Override
    public Page<UserGetResponse> getAllUsers(Pageable pageable) {

        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(this::mapToResponseGetDTO);
    }
    @Transactional
    @Override
    public UserStatusResponseDTO changeStatus(Long id, boolean active) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
            if (user.isActive() == active) {
                throw new UserStatusAlreadySetException(
                        "User status already " + (active ? "active" : "inactive"));
            }


                user.setActive(active);
                userRepository.save(user);

                UserStatusResponseDTO response = new UserStatusResponseDTO();
                response.setUserId(user.getUserId());
                response.setActive(user.isActive());
                response.setMessage("User status updated successfully");

                return response;
    }



}

