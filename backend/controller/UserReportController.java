package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.UserReportRequest;
import RwTool.rwtool.dto.UserReportResponse;
import RwTool.rwtool.service.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user-reports")
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserReportResponse>> assignReport(@Valid @RequestBody UserReportRequest req) {
        UserReportResponse resp = userReportService.assignReport(req);
        return ResponseEntity.ok(ApiResponse.success("User report assigned", resp));
    }

    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<ApiResponse<List<UserReportResponse>>> recent(@PathVariable Long userId) {
        List<UserReportResponse> list = userReportService.getRecentReportsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("Recent reports", list));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<UserReportResponse>>> forUser(@PathVariable Long userId) {
        List<UserReportResponse> list = userReportService.getReportsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User reports", list));
    }
}
