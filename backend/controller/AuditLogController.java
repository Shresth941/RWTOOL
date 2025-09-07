package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.AuditLogDto;
import RwTool.rwtool.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<AuditLogDto>>> getByUser(@PathVariable Long userId) {
        List<AuditLogDto> list = auditLogService.getLogsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("Audit logs", list));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuditLogDto>>> getAll() {
        List<AuditLogDto> list = auditLogService.getAllLogs();
        return ResponseEntity.ok(ApiResponse.success("All audit logs", list));
    }
}
