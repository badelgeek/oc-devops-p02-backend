package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.User;
import com.openclassrooms.etudiant.mapper.UserDtoMapper;
import com.openclassrooms.etudiant.dto.CreateUserRequestDTO;
import com.openclassrooms.etudiant.dto.LoginResponseDTO;
import com.openclassrooms.etudiant.dto.UserDetailDTO;
import com.openclassrooms.etudiant.dto.UserLightDTO;
import com.openclassrooms.etudiant.dto.UserListResponseDTO;
import com.openclassrooms.etudiant.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDtoMapper userDtoMapper;

    public void register(User user) {
        Assert.notNull(user, "User must not be null");
        log.info("Registering new user");

        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("User with login " + user.getLogin() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public LoginResponseDTO login(String login, String password) {
        Assert.notNull(login, "Login must not be null");
        Assert.notNull(password, "Password must not be null");
        Optional<User> user = userRepository.findByLogin(login);
        if (!user.isPresent()) 
            throw new IllegalArgumentException("Invalid credentials");
        User foundUser = user.get();
        if (!passwordEncoder.matches(password, foundUser.getPassword())) 
            throw new IllegalArgumentException("Invalid credentials");
        String token = jwtService.generateToken(foundUser);
        String fullName = foundUser.getFirstName() + " " + foundUser.getLastName();
        return new LoginResponseDTO(token, fullName);
    }

    public UserListResponseDTO getUsers() {
        List<UserLightDTO> users = userRepository.findAll().stream()
        .map(userDtoMapper::toUserLightDTO)
        .toList();
        return new UserListResponseDTO(users);
    }

    public Optional<UserDetailDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userDtoMapper::toUserDetailDTO);
    }

    public void addUser(CreateUserRequestDTO newUser) {
        Assert.notNull(newUser, "User must not be null");
        log.info("Creating new user");

        Optional<User> optionalUser = userRepository.findByLogin(newUser.getLogin());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("User with login " + newUser.getLogin() + " already exists");
        }
        
        User user = userDtoMapper.toEntity(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
