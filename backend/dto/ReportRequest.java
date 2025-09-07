package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRequest {
    @NotBlank
    private String title;

    private String description;

    // file metadata (if client provides)
    private String fileName;
    private String fileType;

    // reference IDs
    private Long reportTypeId;
    private Long createdByUserId;
}
