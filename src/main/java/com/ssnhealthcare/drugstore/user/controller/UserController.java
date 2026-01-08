package com.ssnhealthcare.drugstore.user.controller;

import com.ssnhealthcare.drugstore.user.dto.request.ChangePasswordRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.request.UserUpdateRequestDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.ChangePasswordResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserResponseDTO;
import com.ssnhealthcare.drugstore.user.dto.responce.UserGetResponse;
import com.ssnhealthcare.drugstore.user.dto.responce.UserStatusResponseDTO;
import com.ssnhealthcare.drugstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO dto) {

        return new ResponseEntity<>(
                userService.createUser(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ChangePasswordResponse> changePassword(
            @RequestBody ChangePasswordRequestDTO dto) {

        ChangePasswordResponse response = userService.changePassword(dto);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequestDTO dto) {

        return ResponseEntity.ok(userService.updateUser(id, dto));
   }
    @GetMapping("/getByUser/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }


    @PutMapping("/activate/{id}")
    public ResponseEntity<UserStatusResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(userService.changeStatus(id, true));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<UserStatusResponseDTO> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(userService.changeStatus(id, false));
    }



}
