package com.yuliialazarovych.keep_in_touch.controllers;

import com.yuliialazarovych.keep_in_touch.domain.University;
import com.yuliialazarovych.keep_in_touch.dtos.UserDto;
import com.yuliialazarovych.keep_in_touch.services.UniversityService;
import com.yuliialazarovych.keep_in_touch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserPageController {

    private final UserService userService;
    private final UniversityService universityService;

    @Autowired
    public UserPageController(UserService userService, UniversityService universityService) {
        this.userService = userService;
        this.universityService = universityService;
    }

    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("universities", universityService.getAllUniversities());
        return "user-form";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users";
    }
    @GetMapping("/users")
    public String getAllUsersPage(Model model) {
        List<UserDto> users = userService.getAllUsersDto();
        model.addAttribute("users", users);
        return "users";
    }
    @GetMapping("/users/{username}")
    public String getUserDetails(@PathVariable String username, Model model) {
        UserDto user = userService.getUserByName(username);

        if (user.getUniversityIds() == null) {
            user.setUniversityIds(new HashSet<>());
        }

        System.out.println("User: " + user.getUsername() + ", universityIds: " + user.getUniversityIds());

        Set<String> universityNames = user.getUniversityIds().stream()
                .map(universityId -> {
                    University uni = universityService.getUniversity(universityId);
                    System.out.println("University ID: " + universityId + ", Name: " + uni.getName());
                    return uni.getName();
                })
                .collect(Collectors.toSet());

        model.addAttribute("user", user);
        model.addAttribute("universityNames", universityNames);
        return "user-details";
    }

}
