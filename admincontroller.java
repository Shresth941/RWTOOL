package RwTool.rwtool.controller;

import RwTool.rwtool.entity.ReportType;
import RwTool.rwtool.entity.UserEntity;
import RwTool.rwtool.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    public AdminController(AdminService s) { this.adminService = s; }

    // User Management Endpoints
    @GetMapping("/users") public List<UserEntity> getAllUsers() { return adminService.getAllUsers(); }
    @PostMapping("/users") public UserEntity createUser(@RequestBody UserEntity user) { return adminService.createUser(user); }
    @PutMapping("/users/{id}") public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) { return adminService.updateUser(id, user); }
    @DeleteMapping("/users/{id}") public ResponseEntity<Void> deleteUser(@PathVariable Long id) { adminService.deleteUser(id); return ResponseEntity.noContent().build(); }

    // Report Type (Rule) Management Endpoints
    @GetMapping("/report-types") public List<ReportType> getAllReportTypes() { return adminService.getAllReportTypes(); }
    @PostMapping("/report-types") public ReportType createReportType(@RequestBody ReportType type) { return adminService.createReportType(type); }
    @PutMapping("/report-types/{id}") public ReportType updateReportType(@PathVariable Long id, @RequestBody ReportType type) { return adminService.updateReportType(id, type); }
    @DeleteMapping("/report-types/{id}") public ResponseEntity<Void> deleteReportType(@PathVariable Long id) { adminService.deleteReportType(id); return ResponseEntity.noContent().build(); }
}
