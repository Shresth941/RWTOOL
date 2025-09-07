package RwTool.rwtool.service;

import RwTool.rwtool.dto.RoleDto;
import RwTool.rwtool.dto.UserRequest;
import RwTool.rwtool.dto.UserResponse;
import RwTool.rwtool.entity.Role;
import RwTool.rwtool.entity.User;
import RwTool.rwtool.repo.RoleRepository;
import RwTool.rwtool.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User u = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .mobile(req.getMobile())
                .designation(req.getDesignation())
                .gender(req.getGender())
                .active(true)
                .build();
        u = userRepository.save(u);
        return toDto(u);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return toDto(u);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest req) {
        User u = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            u.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        u.setFullName(req.getFullName());
        u.setMobile(req.getMobile());
        u.setDesignation(req.getDesignation());
        u.setGender(req.getGender());
        userRepository.save(u);
        return toDto(u);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse toDto(User u) {
        UserResponse r = UserResponse.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .fullName(u.getFullName())
                .mobile(u.getMobile())
                .designation(u.getDesignation())
                .gender(u.getGender())
                .active(u.isActive())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();

        if (u.getRoles() != null) {
            Set<RoleDto> roles = u.getRoles().stream().map(role -> RoleDto.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .description(role.getDescription())
                    .build()).collect(Collectors.toSet());
            r.setRoles(roles);
        }
        return r;
    }
}
