package com.example.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDto {
    private Long id;
    private String action;        // e.g., CREATE_USER, UPLOAD_REPORT
    private String entityName;    // e.g., User, Report
    private Long entityId;
    private Long performedByUserId;
    private LocalDateTime performedAt;
    private String details;
}
