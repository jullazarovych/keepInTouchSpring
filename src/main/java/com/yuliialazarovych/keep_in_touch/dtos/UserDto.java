package com.yuliialazarovych.keep_in_touch.dtos;

import com.yuliialazarovych.keep_in_touch.domain.Role;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private Set<Long> universityIds = new HashSet<>();
    private String email;
    private String contactInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
    private String password;
    private Set<String> universityNames= new HashSet<>();
}
