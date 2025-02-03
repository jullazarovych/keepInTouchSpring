package com.yuliialazarovych.keep_in_touch.controllers;

import com.yuliialazarovych.keep_in_touch.dtos.UserDto;
import com.yuliialazarovych.keep_in_touch.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUserDto(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/api/load-all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsersDto();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
    @PostMapping("/api/{userId}/save-event/{eventId}")
    public ResponseEntity<String> saveEventForUser(@PathVariable Long userId, @PathVariable Long eventId) {
        userService.saveEventForUser(userId, eventId);
        return ResponseEntity.ok("Event saved successfully for user with ID: " + userId);
    }

    @DeleteMapping("/api/{userId}/remove-event/{eventId}")
    public ResponseEntity<String> removeEventFromUser(@PathVariable Long userId, @PathVariable Long eventId) {
        userService.removeEventFromUser(userId, eventId);
        return ResponseEntity.ok("Event removed successfully from user with ID: " + userId);
    }

    @GetMapping("/api/search-by-email")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/api/search-by-username")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam String username) {
        UserDto userDto = userService.getUserByName(username);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/api/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/api/exists-by-username")
    public ResponseEntity<Boolean> existsByUsername(@RequestParam String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }
}
