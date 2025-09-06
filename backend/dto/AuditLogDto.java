package RwTool.rwtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {
    private Long logId;
    private Long userId;
    private String action;
    private LocalDateTime timestamp;
    private String details;
}
