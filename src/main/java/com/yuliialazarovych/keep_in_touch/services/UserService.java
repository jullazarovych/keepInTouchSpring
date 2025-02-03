package com.yuliialazarovych.keep_in_touch.services;

import com.yuliialazarovych.keep_in_touch.domain.Event;
import com.yuliialazarovych.keep_in_touch.domain.Role;
import com.yuliialazarovych.keep_in_touch.domain.User;
import com.yuliialazarovych.keep_in_touch.dtos.UserDto;
import com.yuliialazarovych.keep_in_touch.repositories.EventRepository;
import com.yuliialazarovych.keep_in_touch.repositories.UserRepository;
import com.yuliialazarovych.keep_in_touch.domain.University;
import com.yuliialazarovych.keep_in_touch.repositories.UniversityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CrossOrigin
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UniversityRepository universityRepository;
    private final EventRepository eventRepository;

    @Autowired
    public UserService(UserRepository userRepository, EventRepository eventRepository, ModelMapper modelMapper, UniversityRepository universityRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.universityRepository = universityRepository;
        this.eventRepository=eventRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public UserDto getUserByName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

        UserDto userDto = modelMapper.map(user, UserDto.class);

        Set<Long> universityIds = user.getUniversities().stream()
                .map(University::getId)
                .collect(Collectors.toSet());
        userDto.setUniversityIds(universityIds);

        Set<String> universityNames = user.getUniversities().stream()
                .map(University::getName)
                .collect(Collectors.toSet());
        userDto.setUniversityNames(universityNames);

        return userDto;
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public void saveEventForUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventId));

        user.getSavedEvents().add(event);
        userRepository.save(user);
    }
    @Transactional
    public void removeEventFromUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventId));

        user.getSavedEvents().remove(event);
        userRepository.save(user);
    }

    @Transactional
    public List<UserDto> getAllUsersDto() {
        List<User> listUsers = userRepository.findAll();
        return listUsers.stream()
                .map(user -> {
                    UserDto userDto = modelMapper.map(user, UserDto.class);
                    Set<Long> universityIds = user.getUniversities().stream()
                            .map(University::getId)
                            .collect(Collectors.toSet());
                    userDto.setUniversityIds(universityIds);

                    Set<String> universityNames = user.getUniversities().stream()
                            .map(University::getName)
                            .collect(Collectors.toSet());
                    userDto.setUniversityNames(universityNames);

                    return userDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getUserDto(Long id) {
        User user = getUser(id);
        user.getUniversities().size();
        Set<Long> universityIds = user.getUniversities().stream()
                .map(University::getId)
                .collect(Collectors.toSet());
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setUniversityIds(universityIds);
        Set<String> universityNames = user.getUniversities().stream()
                .map(University::getName)
                .collect(Collectors.toSet());
        userDto.setUniversityNames(universityNames);
        return userDto;
    }

    @Transactional
    public ResponseEntity<Void> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("User with ID " + id + " does not exist.");
        }
    }
    @Transactional
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email " + userDto.getEmail() + " is already in use.");
        }
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username " + userDto.getUsername() + " is already taken.");
        }

        Set<University> validUniversities = userDto.getUniversityIds().stream()
                .map(universityId -> universityRepository.findById(universityId)
                        .orElseThrow(() -> new IllegalArgumentException("University with ID " + universityId + " does not exist.")))
                .collect(Collectors.toSet());

        User user = modelMapper.map(userDto, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setUniversities(validUniversities);

        user.setPassword(userDto.getPassword());

        userRepository.save(user);

        UserDto createdUserDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.status(201).body(createdUserDto);
    }

    @Transactional
    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        if (!existingUser.getEmail().equalsIgnoreCase(userDto.getEmail())) {
            if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email " + userDto.getEmail() + " is already in use.");
            }
            existingUser.setEmail(userDto.getEmail());
        }

        if (!existingUser.getUsername().equalsIgnoreCase(userDto.getUsername())) {
            if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new IllegalArgumentException("Username " + userDto.getUsername() + " is already taken.");
            }
            existingUser.setUsername(userDto.getUsername());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(userDto.getPassword());
        }

        if (userDto.getUniversityIds() != null && !userDto.getUniversityIds().isEmpty()) {
            Set<University> validUniversities = userDto.getUniversityIds().stream()
                    .map(universityId -> universityRepository.findById(universityId)
                            .orElseThrow(() -> new IllegalArgumentException("University with ID " + universityId + " does not exist.")))
                    .collect(Collectors.toSet());
            existingUser.setUniversities(validUniversities);
        }

        existingUser.setUpdatedAt(LocalDateTime.now());

        userRepository.save(existingUser);

        UserDto updatedUserDto = modelMapper.map(existingUser, UserDto.class);
        return ResponseEntity.ok(updatedUserDto);
    }

}
