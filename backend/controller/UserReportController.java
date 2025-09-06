package RwTool.rwtool.controller;

import RwTool.rwtool.dto.UserReportRequest;
import RwTool.rwtool.dto.UserReportResponse;
import RwTool.rwtool.service.UserReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-reports")
@CrossOrigin(origins = "http://localhost:3000")
public class UserReportController {

    private final UserReportService service;

    public UserReportController(UserReportService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserReportResponse> create(@RequestBody UserReportRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserReportResponse>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserReportResponse>> byRole(@PathVariable String roleName) {
        return ResponseEntity.ok(service.getByRole(roleName));
    }

    @GetMapping
    public ResponseEntity<List<UserReportResponse>> search(@RequestParam(required = false) String type,
                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate date) {
        return ResponseEntity.ok(service.findByCriteria(type, date));
    }
}
