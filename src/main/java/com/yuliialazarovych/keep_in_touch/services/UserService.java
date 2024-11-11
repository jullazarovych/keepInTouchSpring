package com.yuliialazarovych.keep_in_touch.services;

import com.yuliialazarovych.keep_in_touch.domain.Role;
import com.yuliialazarovych.keep_in_touch.domain.User;
import com.yuliialazarovych.keep_in_touch.dtos.UserDto;
import com.yuliialazarovych.keep_in_touch.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

@CrossOrigin
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findByName(String username) {
        return userRepository.findByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public List<UserDto> getAllUsersDto() {
        List<User> listUsers = userRepository.findAll();
        return listUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public UserDto getUserDto(Long id) {
        User user = userRepository.getById(id);
        return modelMapper.map(user, UserDto.class);
    }
    @Transactional
    public ResponseEntity<UserDto> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new IllegalArgumentException("User with ID " + id + " does not exist.");
        }
    }
    @Transactional
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) {
        User newUser = modelMapper.map(userDto, User.class);
        User oldUser = getUser(id);
        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            newUser.setPassword(oldUser.getPassword());
        }
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setId(id);
        newUser.setRole(oldUser.getRole());
        newUser.setEmail(oldUser.getEmail());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
