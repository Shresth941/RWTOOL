package RwTool.rwtool.controller;

import RwTool.rwtool.dto.ApiResponse;
import RwTool.rwtool.dto.NotificationDto;
import RwTool.rwtool.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> forUser(@PathVariable Long userId) {
        List<NotificationDto> list = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched", list));
    }

    @PostMapping("/mark-read/{id}")
    public ResponseEntity<ApiResponse<String>> markRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Marked as read", null));
    }
}
