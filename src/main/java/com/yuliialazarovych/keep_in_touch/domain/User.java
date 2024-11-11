package com.yuliialazarovych.keep_in_touch.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 24)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String university;

    @Column(columnDefinition = "TEXT")
    private String contactInfo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @PreUpdate
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
