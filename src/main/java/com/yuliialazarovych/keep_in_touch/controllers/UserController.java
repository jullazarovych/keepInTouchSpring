package com.yuliialazarovych.keep_in_touch.controllers;
import com.yuliialazarovych.keep_in_touch.domain.User;
import com.yuliialazarovych.keep_in_touch.dtos.UserDto;
import com.yuliialazarovych.keep_in_touch.services.UserService;
import com.yuliialazarovych.keep_in_touch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.awt.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserDto(id);
    }

    @GetMapping("/load-all")
    public List<UserDto> getAllUser() {
        return userService.getAllUsersDto();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, UserDto user) {
       return userService.updateUser( id,user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}

