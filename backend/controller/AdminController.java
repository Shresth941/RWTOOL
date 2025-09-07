package RwTool.rwtool.controller;

import RwTool.rwtool.dto.*;
import RwTool.rwtool.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // all endpoints inside require ADMIN role
public class AdminController {

    private final AdminService adminService;

    // Users
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest req) {
        return ResponseEntity.ok(ApiResponse.success("User created", adminService.createUser(req)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest req) {
        return ResponseEntity.ok(ApiResponse.success("User updated", adminService.updateUser(id, req)));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers() {
        return ResponseEntity.ok(ApiResponse.success("Users fetched", adminService.getAllUsers()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted", null));
    }

    // Roles
    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(ApiResponse.success("Role created", adminService.createRole(roleDto)));
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<RoleDto>>> getRoles() {
        return ResponseEntity.ok(ApiResponse.success("Roles fetched", adminService.getAllRoles()));
    }

    // Manual ingestion trigger
    @PostMapping("/ingest")
    public ResponseEntity<ApiResponse<String>> triggerIngest(@RequestParam String filePath, @RequestParam String destination) {
        adminService.triggerIngestion(filePath, destination);
        return ResponseEntity.ok(ApiResponse.success("Ingestion triggered", null));
    }
}
