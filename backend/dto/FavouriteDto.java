package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDto {
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long reportId;

    private LocalDateTime createdAt;
}
