package RwTool.rwtool.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportRequest {
    private String name;
    private String path;
    private LocalDateTime generatedDate;
    private String uploadedBy;
    private Long reportTypeId;   // optional
}
