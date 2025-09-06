package RwTool.rwtool.controller;

import RwTool.rwtool.dto.UserRequest;
import RwTool.rwtool.dto.UserResponse;
import RwTool.rwtool.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService service;

    public UserController(UserService service) { this.service = service; }

    // Create user (signup) - admin or self-registration
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest req) {
        return ResponseEntity.ok(service.createUser(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserResponse>> byRole(@PathVariable String roleName) {
        return ResponseEntity.ok(service.getByRole(roleName));
    }

    @PostMapping("/{userId}/favorites/{reportId}")
    public ResponseEntity<Void> toggleFavorite(@PathVariable Long userId, @PathVariable Long reportId) {
        service.toggleFavorite(userId, reportId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<Set<Long>> getFavorites(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getFavoriteReportIds(userId));
    }
}
