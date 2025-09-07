package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportTypeDto {
    private Long id;

    @NotBlank
    private String name;

    private String description;
}
