package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReportRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long reportId;

    private String status; // PENDING, APPROVED, etc.
    private String comments;
}
