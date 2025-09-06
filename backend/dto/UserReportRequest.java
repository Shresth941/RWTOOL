package RwTool.rwtool.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserReportRequest {
    private String title;
    private String content;
    private String reportType;
    private LocalDate reportDate;
    private String category;
    private Long userId;
    private Long roleId;
}
