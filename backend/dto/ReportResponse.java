package RwTool.rwtool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long id;
    private String name;
    private String path;
    private LocalDateTime generatedDate;
    private String uploadedBy;
    private Long reportTypeId;
}
