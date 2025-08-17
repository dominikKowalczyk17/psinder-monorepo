package com.dkowalczyk.psinder_app.service;

import com.dkowalczyk.psinder_app.dto.*;
import com.dkowalczyk.psinder_app.model.User;
import com.dkowalczyk.psinder_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DogService dogService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToDto(user);
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByName(request.getName())) {
            throw new RuntimeException("User already exists with name: " + request.getName());
        }

        User user = convertToEntity(request);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    @Transactional
    public UserDto updateUser(Long id, CreateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setName(request.getName());
        existingUser.setAge(request.getAge());
        existingUser.setBio(request.getBio());

        if (request.getDog() != null) {
            existingUser.setDog(dogService.convertToEntity(request.getDog()));
        }

        User savedUser = userRepository.save(existingUser);
        return convertToDto(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto registerUser(CreateUserRequest request) {
        if (userRepository.existsByName(request.getName())) {
            throw new RuntimeException("User already exists with name: " + request.getName());
        }

        // BUSINESS RULE: Every user must have a dog for dog walking app
        if (request.getDog() == null) {
            throw new RuntimeException("Dog information is required for registration");
        }

        if (!isValidDogRequest(request.getDog())) {
            throw new RuntimeException("Dog must have at least a name and size to register");
        }

        User user = new User();
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAge(request.getAge());
        user.setBio(request.getBio());
        user.setRole("USER");

        // Dog is guaranteed to be valid at this point
        user.setDog(dogService.convertToEntity(request.getDog()));

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    private boolean isValidDogRequest(CreateDogRequest dogRequest) {
        // For dog walking app, we need at least name and size
        return dogRequest.getName() != null && !dogRequest.getName().trim().isEmpty() &&
           dogRequest.getSize() != null && !dogRequest.getSize().trim().isEmpty();
    }


    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setBio(user.getBio());

        if (user.getDog() != null) {
            dto.setDog(dogService.convertToDto(user.getDog()));
        }

        return dto;
    }

    private User convertToEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setBio(request.getBio());

        if (request.getDog() != null) {
            user.setDog(dogService.convertToEntity(request.getDog()));
        }

        return user;
    }
}