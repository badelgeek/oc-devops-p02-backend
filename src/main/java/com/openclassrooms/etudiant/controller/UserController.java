package com.openclassrooms.etudiant.controller;

import com.openclassrooms.etudiant.dto.LoginRequestDTO;
import com.openclassrooms.etudiant.dto.LoginResponseDTO;
import com.openclassrooms.etudiant.dto.UserDetailDTO;
import com.openclassrooms.etudiant.dto.UserListResponseDTO;
import com.openclassrooms.etudiant.dto.CreateUserRequestDTO;
import com.openclassrooms.etudiant.mapper.UserDtoMapper;
import com.openclassrooms.etudiant.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequestDTO registerDTO) {
        userService.register(userDtoMapper.toEntity(registerDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = userService.login(
                loginRequestDTO.getLogin(),
                loginRequestDTO.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/users")
    public ResponseEntity<UserListResponseDTO> getUsers() {
        UserListResponseDTO response = userService.getUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserDetailDTO> getUser(@PathVariable Long id) {
        Optional<UserDetailDTO> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody CreateUserRequestDTO newUser) {
        userService.addUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
