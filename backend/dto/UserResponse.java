package com.example.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String mobile;
    private String designation;
    private String gender;
    private boolean active;
    private Set<RoleDto> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
