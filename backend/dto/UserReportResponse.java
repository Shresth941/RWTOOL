package com.example.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportResponse {
    private Long id;
    private UserResponse user;
    private ReportResponse report;
    private String status;
    private String comments;
    private LocalDateTime assignedAt;
    private LocalDateTime lastAccessedAt;
}
